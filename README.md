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

# 훈련데이터1
- 약 20000장
- train 대비 validation 30% 사용


![model1(overfiting)](https://github.com/ljxxyxxg/AI_Project/assets/152591039/12da38b1-572f-4b05-9835-27a2c50b32f6)

# 훈련데이터2
- 약 6000장
- train 대비 validation 30% 사용


![model1](https://github.com/ljxxyxxg/AI_Project/assets/152591039/5318acc7-cb67-4bd5-9cfb-4025e6d6963f)

- 결과 : 너무 많은 데이터셋은 학습에 있어서 좋지 않다

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
4. Firebase Realtime Database에 데이터가 저장되고, Android 앱에서 해당 데이터를 읽어와 표시합니다.

# Dataset 캐글 참조
https://www.kaggle.com/datasets/prasadvpatil/mrl-dataset

# 참고

이 코드는 OpenCV, Keras, Tkinter 등 다양한 라이브러리를 활용하여 구현되었습니다.
현재 모델은 용량으로 인해 제외하였습니다.

# 참고영상
https://github.com/ljxxyxxg/AI_Project/assets/152591039/1ef58427-bbe8-4a86-9e20-34dda87ce604







