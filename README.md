# CNN_Drowsiness_Detection

이 프로젝트는 웹캠 영상을 기반으로 운전자의 졸음 상태를 감지하고 경고하는 프로그램
OpenCV와 Keras를 사용하여 구현되었으며, Firebase를 통해 데이터를 저장할 수 있습니다.

# 사용한 라이브러리

- OpenCV
- Keras
- NumPy
- Pygame
- Tkinter
- PIL
- Firebase Admin SDK

# 주요 기능

- 웹캠 영상을 통한 운전자의 눈 상태 감지
- 눈 감음 시 경고음 및 시각적 경고 표시
- 졸음 운전 감지 시 Firebase를 통한 데이터 저장
- 모바일 앱을 통한 실시간 졸음 운전 정보 조회
  
![화면 캡처 2024-07-01 113023](https://github.com/ljxxyxxg/AI_Android_Project/assets/152591039/fc1242d6-5479-4abb-a41a-332fc510b704)

# 모델학습
- 약 6000장
- train 대비 validation 30% 사용

![model1](https://github.com/ljxxyxxg/AI_Project/assets/152591039/5318acc7-cb67-4bd5-9cfb-4025e6d6963f)


# 프로그램 실행 방법

1. Python 3.8 버전이상.
2. 필요한 라이브러리를 설치합니다. (`pip install opencv-python keras numpy pygame pillow firebase-admin 등`)
3. `Firebase참조.json` 파일에 Firebase 프로젝트의 인증 정보를 제공합니다.
4. `CNN__model.h5`와 `haarcascade` 폴더를 포함한 모든 필요한 파일을 프로젝트 폴더에 추가합니다.
5. 코드를 실행합니다. (`detectionButton.py`)
6. GUI를 통해 시스템을 시작, 중지, 재시작 할 수 있습니다.

# Android 앱 실행 방법

1. Android Studio를 사용하여 프로젝트를 엽니다.
2. Firebase Console에서 Firebase 프로젝트를 생성하고, Realtime Database를 설정합니다.
3. `google-services.json` 파일을 프로젝트에 추가합니다.
4. Firebase Realtime Database에 데이터가 저장되고, Android 앱에서 해당 데이터를 읽어와 표시합니다. 실시간으로 졸음 운전 시간 및 정보 확인 가능

# Dataset 캐글 참조
https://www.kaggle.com/datasets/prasadvpatil/mrl-dataset

# 참고
이 코드는 OpenCV, Keras, Tkinter 등 다양한 라이브러리를 활용하여 구현되었습니다.
현재 모델은 용량으로 인해 제외하였습니다.

# 실행화면 
- CCTV 메인 화면
 
![메인화면](https://github.com/JOJUNHYUNG0818/Drowsiness_Detection/assets/152590602/b5285fa2-2244-4f1d-aca2-064b81cc5fec)

- 안전 상태 opene yes
 
  ![blackopen](https://github.com/JOJUNHYUNG0818/Drowsiness_Detection/assets/152590602/d679a42c-9cee-40dd-b3df-44fd7c9f6d00)
  
- 안전 상태 close eyes (Score +1)
  
![blackclose](https://github.com/JOJUNHYUNG0818/Drowsiness_Detection/assets/152590602/eaab1b42-0a4c-4875-ad47-b604573e1f7b)

- Score 10 이상시 레드 화면 open eyes( Score -1) 
 
![redopen](https://github.com/JOJUNHYUNG0818/Drowsiness_Detection/assets/152590602/dee0f54b-3ecb-4058-8a95-08fe7183c878)

- Score 10 이상시 레드 화면 close eyes (Score +1)

![redclose](https://github.com/JOJUNHYUNG0818/Drowsiness_Detection/assets/152590602/520745f9-3e27-4435-a5a0-05b409629647)

- 졸음운전 기록 앱으로 받아오기
  
![졸음운전2](https://github.com/JOJUNHYUNG0818/Drowsiness_Detection/assets/152590602/30ff11c1-42d2-4651-a610-caf4bfc244ff)

# 참고영상
https://github.com/ljxxyxxg/AI_Project/assets/152591039/1ef58427-bbe8-4a86-9e20-34dda87ce604

# 추가 업데이트 기능
1. ITS 국가교통정보센터(National Transport Information Center) 오픈 API를 활용
   실시간 교통사고 현황 조회 가능하게 업데이트 하였습니다.

![json오픈api](https://github.com/ljxxyxxg/AI_Android_Project/assets/152591039/743331f7-1268-418b-b843-1cce8ca94833)
![오픈api 사고정보](https://github.com/ljxxyxxg/AI_Android_Project/assets/152591039/186d65b7-c0c0-42b2-ae44-59e9214e8299)


2. Tkinter 사용하여 UI부분에 운전자의 운전시간 기록확인 가능하게 업데이트 하였습니다.


운전시간(전)
![운전시간(전)](https://github.com/ljxxyxxg/AI_Android_Project/assets/152591039/41efa58f-bc2c-44c8-b7a9-57b079928deb)

운전시간(후)
![운전시간(후)](https://github.com/ljxxyxxg/AI_Android_Project/assets/152591039/0f29a263-3dda-4c08-9896-2f24874009c8)

코드구현
![코드구현](https://github.com/ljxxyxxg/AI_Android_Project/assets/152591039/ea296cb1-9810-45fe-b246-5dfdee435853)






