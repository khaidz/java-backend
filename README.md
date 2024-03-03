# Base project cho Springboot
### Phiên bản sử dụng
- spring-boot: 3.2.3
- spring-security: 6.2.2

### Cài đặt môi trường
- Java 17
- Redis
- Postgresql
  + Tạo 1 database 
  + Chạy file sql trong đường dẫn: ***src/main/resources/sql/init.sql***
- Sửa các thông tin trong file ***application.properties***
- Run project

### Hướng dẫn cài đặt trên linux (ubuntu 20.04)
- SDKMan (https://sdkman.io/install): Dùng để quản lý version java
  + ```sudo apt install curl zip unzip -y```
  + ```curl -s "https://get.sdkman.io" | bash```
  + ```source "$HOME/.sdkman/bin/sdkman-init.sh"```
- Chọn phiên bản java 17 cần cài đặt
  + ```sdk list java```
  + ```sdk install java 17.0.10-oracle```
  + Kiểm tra đã cài đặt thành công hay chưa
    + ```java --version``` 
- Cài đặt redis
  + ```sudo apt install redis```
  + Kiểm tra đã cài đặt thành công chưa
    + ```redis-cli -v```
- Cài đặt postgresql
  + ```sudo apt install postgresql postgresql-contrib -y```
  + Đảm bảo dịch vụ đang chạy
    + ```sudo systemctl start postgresql.service```
    + Tạo 1 user mới
      + ```sudo -u postgres psql```
      + ```create user khaibq with password '123456';```
    + Tạo database
      + ```create database java_backend;```
    + Gán quyền cho user ***khaibq*** vừa tạo ở trên
      + ```grant all privileges on database java_backend to khaibq;```
      + ```flush```
  - [cheat-sheet] (https://karloespiritu.github.io/cheatsheets/postgresql/)
- Cài đặt nodejs
  + ```curl -o- https://raw.githubusercontent.com/nvm-sh/nvm/v0.39.3/install.sh | bash```
  + Mở 1 terminal mới
  + ```nvm ls-remote```
  + ```nvm install v20.11.0```
  + Kiểm tra lại ```node --version```
- Cài đặt pm2
  + ```npm install -g pm2```
- Xong việc cài đặt môi trường
- Đẩy file .jar lên ubuntu và thực hiện chạy lệnh sau để chạy 
  + ```pm2 start "java -jar /path/to/jar/java_backend.jar" --name "java_backend"``` 
  + ```pm2 l``` để xem danh sách 
  + ```pm2 log 0 --lines 1000``` để xem log. Thay 0 bằng giá trị id ở trên
