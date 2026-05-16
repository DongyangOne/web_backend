
# 1. Java 21 환경 사용 (사용하시는 버전이 다르면 수정 가능)
FROM eclipse-temurin:21-jdk

# 2. 작업 디렉토리 설정
WORKDIR /app

# 3. 빌드된 jar 파일을 컨테이너 내부로 복사
# (보통 build/libs/ 아래에 생성됩니다)
COPY build/libs/backend-0.0.1-SNAPSHOT.jar /app.jar

# 4. 앱 실행
ENTRYPOINT ["java", "-jar", "/app.jar"]
