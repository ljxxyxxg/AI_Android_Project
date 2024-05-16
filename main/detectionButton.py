import cv2
import os
from keras.models import load_model
import numpy as np
from pygame import mixer
import tkinter as tk
from tkinter import *
from PIL import Image, ImageTk
import sqlite3
import firebase_admin
from firebase_admin import credentials
from firebase_admin import db
import datetime

# Fire_base 연동
cred = credentials.Certificate('internet참고.json')
firebase_admin.initialize_app(cred,{
'databaseURL' : 'https://internet참고.firebasedatabase.app/'
})

sys_running = False
drowsy_driving_detected = False

# GUI 요소 업데이트하는 함수
mixer.init()
sound = mixer.Sound('alarm.wav')
alarm_playing = False

# opencv xml 코드
face = cv2.CascadeClassifier('haarcascade/haarcascade_frontalface_alt.xml')
leye = cv2.CascadeClassifier('haarcascade/haarcascade_lefteye_2splits.xml')
reye = cv2.CascadeClassifier('haarcascade/haarcascade_righteye_2splits.xml')
eyes = cv2.CascadeClassifier('haarcascade/haarcascade_eye.xml')

# 라벨링
lbl=['Closed eyes','Open eyes']

# 모델로드
model = load_model('CNN__model11.h5')

# 전역 변수로 사용할 변수들
b_count = 0

path = os.getcwd()

# 웹캠 열기
cap = cv2.VideoCapture(0) 

# 변수 초기화
font = cv2.FONT_HERSHEY_COMPLEX_SMALL
count = 0
score = 0
thicc = 2
rpred = [99]
lpred = [99]
deathEye = ""
eyeList = []

curLeftEyeValue = 0
preLeftEyeValue = 0
curRigEyeValue = 0
preRigEyeValue = 0

curScore = 0
preScore = 0

# Tkinter 애플리케이션 생성
root = tk.Tk()
root.title("Drowsiness Detection")  # 타이틀 설정
root.geometry("700x600")

# 웹캠 비디오를 표시할 레이블 생성
label = tk.Label(root)
label.pack(anchor="center")

# 메인
def update_frame():
    global cap, alarm_playing, model, path, font, count, score, thicc,rpred, lpred, deathEye, eyeList, curLeftEyeValue, preLeftEyeValue, curRigEyeValue, preRigEyeValue,curScore,preScore

    if sys_running:
      ret, frame = cap.read()
      if ret:
        height,width = frame.shape[:2] 


        gray = cv2.cvtColor(frame, cv2.COLOR_BGR2GRAY) 
        faces = face.detectMultiScale(gray,minNeighbors=5,scaleFactor=1.1,minSize=(25,25)) #Face detection
        eye=eyes.detectMultiScale(gray) #eye detection
        left_eye = leye.detectMultiScale(gray) #Left eye detection
        right_eye =  reye.detectMultiScale(gray) #Right eye detection
        
        cv2.rectangle(frame, (0,height-50) , (200,height) , (0,0,0) ,thickness=cv2.FILLED)

        for (x,y,w,h) in faces:
            cv2.rectangle(frame, (x,y) , (x+w,y+h) , (200,200,200) , 1) 
            
        for (x,y,w,h) in eye:
            cv2.rectangle(frame, (x,y),(x+w,y+h) ,(100,100,100) , 1)


        for (x,y,w,h) in right_eye:
            r_eye=frame[y:y+h,x:x+w]
            count=count+1
            r_eye = cv2.cvtColor(r_eye,cv2.COLOR_BGR2GRAY)
            r_eye = cv2.resize(r_eye,(100,100)) 
            r_eye= r_eye/255                  
            r_eye=  r_eye.reshape(100,100,-1)
            r_eye = np.expand_dims(r_eye,axis=0)
            rpred = model.predict(r_eye)
            idx1 = np.argmax(rpred, axis=-1)

            curRigEyeValue=rpred
            rpred = (curRigEyeValue+preRigEyeValue)/2
            preRigEyeValue=curRigEyeValue
            print("right=>")
            print(rpred)

            if(rpred[0]==1):
                lbl='Open.' 
            if(rpred[0]==0):
                lbl='Closed.'
            break

        for (x,y,w,h) in left_eye:
            l_eye=frame[y:y+h,x:x+w]
            count=count+1
            l_eye = cv2.cvtColor(l_eye,cv2.COLOR_BGR2GRAY)  
            l_eye = cv2.resize(l_eye,(100,100))
            l_eye= l_eye/255
            l_eye=l_eye.reshape(100,100,-1)
            l_eye = np.expand_dims(l_eye,axis=0)
            lpred = model.predict(l_eye)
            idx2 = np.argmax(lpred, axis=-1)


            curLeftEyeValue=lpred
            lpred = (curLeftEyeValue+preLeftEyeValue)/2
            preLeftEyeValue=curLeftEyeValue
            print("left=>")
            print(lpred)

            if(lpred[idx2]==1):
                lbl='Open..'   
            if(lpred[idx2]==0):
                lbl='Closed..'
            break

        if(rpred[0]<=0.5 and lpred[0]<=0.5): # 0.3--> 0.5로 변경
            score=score+1
            cv2.putText(frame,"Closed.!.",(10,height-20), font, 1,(255,255,255),1,cv2.LINE_AA)
            deathEye="a"
            print("Value = ")
            print(deathEye)
        
        else:
            score=score-1
            cv2.putText(frame,"Open.!.",(10,height-20), font, 1,(255,255,255),1,cv2.LINE_AA)
            deathEye="b"
            print("Value = ")
            print(deathEye)

        if(score<0):
            score=0   
        cv2.putText(frame,'Score:'+str(score),(100,height-20), font, 1,(255,255,255),1,cv2.LINE_AA)

        if(score>10):
            drowsy_driving_detected = True

            # 알람 설정
            cv2.imwrite(os.path.join(path,'image.jpg'),frame)
            if not alarm_playing:
                try:
                    sound.play()
                    alarm_playing = True
                    print("soundPlay")
                except:  
                    pass

            # 화면 빨간창 들어오게 하기
            if(thicc<16):
                thicc= thicc+2
            else:
                thicc=thicc-2
                if(thicc<2):
                    thicc=2

            cv2.rectangle(frame,(0,0),(width,height),(0,0,255),thicc) 

            eyeList.append(deathEye)
            
            print("eyeList=")
            print(eyeList)
            for eye in eyeList:
                if eye == 'b':
                    b_count += 1
                if eye== 'a':
                    b_count=0
                if b_count >= 7:
                    score = 0
                    print("점수 초기화됨")
                    eyeList=[]
                
        else:
            if alarm_playing:
                sound.stop()
                alarm_playing = False
        
        curScore = score
        print(curScore)

        if score==10 :
            current_time = datetime.datetime.now()
            if curScore>preScore:
                ref = db.reference('id_list')
                data = { 
                    'timestamp': current_time.strftime("%Y-%m-%d %H:%M:%S"),
                    'id': 'time',
                    'gender': 'MEN',
                    'age': 28,
                    'name': 'KIM',
                    'number': 0,
                }
                ref.push(data)
        preScore = curScore

        # 프레임을 tkinter에서 표시할 수 있는 RGB 형식으로 변환
        frame_rgb = cv2.cvtColor(frame, cv2.COLOR_BGR2RGB)
        
        # 프레임 크기를 조정
        frame_resized = cv2.resize(frame_rgb, (500,500))

        # ImageTk 형식으로 프레임을 변환
        img = Image.fromarray(frame_resized)
        imgtk = ImageTk.PhotoImage(image=img)

        # Label 위젯에 최신 프레임을 업데이트
        label.imgtk = imgtk
        label.configure(image=imgtk)

        if sys_running:
            label.after(10, update_frame)  # 시스템이 실행 중이면 다음 프레임 예약



# Start/Stop 버튼의 command 함수를 수정하여 스레드 시작 및 중지
def button_system():
    global sys_running, cap
    if sys_running:
        sys_running = False
        startstop_button.config(text="Start system")
        if cap is not None:
            cap.release()
            cap = None
    else:
        sys_running = True
        startstop_button.config(text="Stop system")
        cap = cv2.VideoCapture(0)
        update_frame()

# 점수를 강제로 초기화하는 함수
def reset_score():
    global score
    score = -1

# 프로그램 종료하는 함수
def program_out() :
    global cap
    cv2.destroyAllWindows()  # OpenCV 창 닫기
    root.destroy()  # Tkinter 창 닫기

# Start/Stop 버튼 생성
startstop_button = Button(root, text="Start/Stop", command=button_system, width=10, height=4)
startstop_button.pack( side='left', padx=115, pady=30)

# 점수 초기화 버튼 생성
reset_button = Button(root, text="Reset Score", command=reset_score, width=10, height=4)
reset_button.pack( side='left', padx=115, pady=30) 

# 종료 버튼
out_button = Button(root, text="Program Out", command=program_out, width=10, height=4)
out_button.pack( side='left',padx=115, pady=30)

# Tkinter 애플리케이션 실행
root.mainloop()

# 프로그램 종료 시 카메라 해제
if cap is not None:
    cap.release()