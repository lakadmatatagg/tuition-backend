mvn spring-boot:run -Drun.jvmArguments="-Xms256m -Xmx2048m" -Dspring-boot.run.jvmArguments="-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=9009" -Dspring.profiles.active=dev
