# HelloUser
Simple member page prototype – Spring Boot + Thymeleaf

## Features

- View a list of members
- Add new members (admin only)
- Delete members (admin only)
- Admin login/logout
- Responsive header navigation (Home, Members, Add Member, Logout, Admin Login)
- Member data stored in `members.json`

## Technologies

- Java 21
- Spring Boot 3
- Thymeleaf
- Maven

## Project Structure

- `src/main/java/com/HelloUser/HelloUser/` – Java source code (controllers, models)
- `src/main/resources/templates/` – Thymeleaf HTML templates
- `src/main/resources/static/css/` – CSS styles
- `src/main/resources/members.json` – Member data storage

## How to Run

1. Make sure you have Java 21 and Maven installed.
2. Build and run the project:
   ```
   mvn spring-boot:run
   ```
3. Open your browser and go to [http://localhost:8080](http://localhost:8080)

## Usage

- Navigate using the header links.
- Admins can log in to add or delete members.
- Member information is displayed and managed via the web interface.


