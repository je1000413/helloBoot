FROM gradle:7.6.1-jdk17-alpine AS builder
WORKDIR /app
ADD build.gradle /app
RUN gradle build -x test --parallel --continue > /dev/null 2>&1 || true

COPY . /app
RUN gradle clean build --no-daemon --exclude-task test

FROM azul/zulu-openjdk:17
ARG app_name="c2v_mice_backoffice_api"
ARG active
ENV ACTIVE ${active}
ENV APPNAME ${app_name}

COPY --from=builder /app/conf/application-${active}.yml /${app_name}/conf/application.yml
COPY --from=builder /app/conf/logback.xml /${app_name}/conf/logback.xml
COPY --from=builder /app/conf/version.properties /${app_name}/conf/version.properties
COPY --from=builder /app/build/libs/ /${app_name}/lib/
COPY --from=builder /app/startup /${app_name}/startup

RUN sed -i -e 's/\r$//' /${app_name}/startup/startup.sh
RUN chmod +x /${app_name}/startup/startup.sh
ENTRYPOINT ["/bin/bash","-c","/$APPNAME/startup/startup.sh console $ACTIVE"]