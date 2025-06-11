
# ToDoList Desktop App (Java Swing)

심플하고 직관적인 ToDo 관리 프로그램입니다.  
FlatLaf를 기반으로 한 모던한 UI와 로컬 파일 기반 저장 기능을 제공합니다.

![스크린샷 2025-06-11 224545](https://github.com/user-attachments/assets/1bc9ccc5-c712-41d5-ad1a-c3577be5b8e0)



---

## 🔍 프로젝트 배경

“자바로 나만의 실행 프로그램(EXE)을 만들어보고 싶다”는 아이디어에서 출발한 개인 프로젝트입니다.  
실행 파일만으로 동작하는 자바 앱을 만드는 과정을 학습하고자 하였고,  
그 과정에서 `Swing`, `파일 입출력`, `날짜 처리`, `런처 도구` 등을 실습했습니다.

## ✨ Features
- 날짜별 ToDo 목록 작성
- 체크 시 취소선 및 저장 처리
- JTree를 통한 날짜별 ToDo 기록 조회
- `.txt` 파일 기반 로컬 데이터 저장
- FlatLaf 기반 디자인 / Icons8 아이콘

## 🔧 실행 방법

1. `ToDoApp.exe` 실행 (혹은 `todolist.jar` 더블클릭)
2. 기본은 `오늘 날짜` 기준이나, ◀▶ 버튼을 통해 다른 날짜도 선택해 할 일을 작성할 수 있습니다.
3. 체크 후 닫으면 자동 저장
4. 왼쪽 아이콘 클릭 시 저장된 날짜 목록(JTree) 열람 가능

📌 **자바 설치 필요 (JRE 17 이상)**  

→ 설치 안 되어 있으면 실행이 안 될 수 있습니다

## 📦 사용된 라이브러리 및 리소스
- [FlatLaf](https://github.com/JFormDesigner/FlatLaf) by JFormDesigner  
  Licensed under Apache License 2.0
- [Gson](https://github.com/google/gson) by Google  
  Licensed under Apache License 2.0
- Icons by [Icons8](https://icons8.com/icons)
이 프로젝트는 Java Swing을 기반으로 만들어졌으며, `.jar` 파일을 `Launch4j`를 사용해 `.exe` 파일로 패키징하였습니다.
`Launch4j`는 Java 애플리케이션을 Windows 실행 파일로 만들어주는 도구입니다.



<p align="left">
  <img src="https://github.com/user-attachments/assets/d59e94b1-08d8-4172-a511-a120c0eeea73" />
  <img src="https://github.com/user-attachments/assets/8a2fd257-e323-452b-b73f-9bc2b925336d" width="10%" />
</p>
