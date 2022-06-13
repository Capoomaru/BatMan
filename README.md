# BatMan
해당 프로그램은 안드로이드 스튜디오의 아래 버전에서 작성되었습니다.  
Android Studio Chipmunk | 2021.2.1  
Build #AI-212.5712.43.2112.8512546, built on April 29, 2022  
Runtime version: 11.0.12+7-b1504.28-7817840 amd64  
VM: OpenJDK 64-Bit Server VM by Oracle Corporation  
Windows 10 10.0  
GC: G1 Young Generation, G1 Old Generation  
Memory: 4096M  
Cores: 16  
Registry: external.system.auto.import.disabled=true  

min SDK : 26 (안드로이드 oreo)  

소스코드 경로 :   
(java) ./BatMan/app/src/main/java/com/example/batman 하위 디렉토리  
(drawble/xml) ./BatMan/app/src/main/res 하위 디렉토리


사용법 :  
1. 안드로이드 스튜디오 2021.2.1 버전 설치(https://developer.android.com/studio?gclid=Cj0KCQjwwJuVBhCAARIsAOPwGARs8q4s9boa-pr_oywtdZp-l47qDzJJT-Ug_cvejpGJXpRjzwS-_g4aAn4LEALw_wcB&gclsrc=aw.ds)  

설치 선택 요소  
Install Type : standard next  
Select UI Theme : anything  
SDK Components Setup : 오른쪽 아래의 android Framework detected   
  

(**추천**)깃 주소를 활용한 실행 :  
File - new - Project from Version Control  
version control : Git 선택  
URL : https://github.com/Capoomaru/BatMan.git 입력  
Directory : 기본으로 지정되는 디렉토리를 이용하거나, 원하는 위치로 이동(단, 한글명이 포함된 경로 X)  
clone 클릭  
신뢰하는 프로젝트(Trust Project) 선택  
현재 창에 표시를 원할 시 this window / 새로운 창을 원할 경우 new window를 선택

  
다운 받은 소스코드 활용하여 실행 :   
다운받은 BatMan.zip 파일을 공백, 한글이 포함되지 않는 경로에 압축 해제하고 설치 이후   
안드로이드 스튜디오 실행 후 팝업창이 뜬 경우 : Open - 해당경로(안드로이드 아이콘이 표시되는) 선택 - 신뢰하는 프로젝트 선택  
이미 안드로이드 스튜디오 실행된 경우 : File - New - Import Project - 해당경로(안드로이드 아이콘이 표시되는) 선택 - Create project from existing sources 선택 후 next  
프로젝트를 저장하고자하는 경로 설정(기존 위치와 다른) 후 next  
모두 체크하고 next  
finish 클릭  
현재 창에 표시를 원할 시 this window / 새로운 창을 원할 경우 new window를 선택  
  
사용할 안드로이드 장치(안드로이드 버전 oreo(8) 이상)가 있는 경우,   
zip 파일 내부의 apk 파일을 스마트폰으로 옮겨 직접 설치하거나  
개발자 모드를 활성화하여 PC와 유선연결하여 빌드(https://haruple.tistory.com/162)  

사용할 안드로이드 장치가 없는 경우,  
안드로이드 스튜디오 프로젝트를 실행한 후 Tools - Device Manager 선택  
Create device 선택 - 테스트용으로 사용된 Pixel 4 XL(이외의 장치도 무관함) 선택 후 next  
oreo이후의 SDK만 지원하므로 API Level이 26 이상인 것 중에 하나를 선택해서 DownLoad클릭  
설치된 버전을 선택하고 Next 클릭  
Finish 클릭  

이제 실행을 위해 오른쪽 상단의 app 오른쪽의 스피너를 클릭하여 방금 설치한 장치를 선택하고 바로 오른쪽의 Run 'app'(삼각형) 버튼 클릭  
이제 장치에서 실행된 어플을 구동하면 됩니다.  
(가상 장치의 경우, 한글 입력기를 따로 설치하여야하며, 한글 입력에 대한 버그가 많아 정상적인 테스트 진행이 어려우므로 실제 안드로이드 기기를 이용할 것을 적극 권장합니다. )  
