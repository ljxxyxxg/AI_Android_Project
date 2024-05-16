from keras.models import Sequential
from keras.layers import Conv2D, MaxPooling2D, Flatten, Dropout, Dense, BatchNormalization
from keras.preprocessing.image import ImageDataGenerator
from keras.callbacks import EarlyStopping




train = ImageDataGenerator(rescale=1/255,shear_range=0.2,zoom_range=0.2,horizontal_flip=True)
validation = ImageDataGenerator(rescale=1/255)

train_image_gen = train.flow_from_directory('Train dataset',target_size=(100,100),batch_size=32,color_mode='grayscale',class_mode='binary')
valid_image_gen = validation.flow_from_directory('Valid dataset',target_size=(100,100),batch_size=32,color_mode='grayscale',class_mode='binary')

batch_size=32
SPE= len(train_image_gen.classes)//batch_size
VS = len(valid_image_gen.classes)//batch_size 
print(SPE,VS)

print(train_image_gen.class_indices)

model = Sequential()

# Block 1
model.add(Conv2D(64, (3,3), input_shape=(100, 100, 1), activation='relu', padding='same'))
model.add(Conv2D(64, (3,3), activation='relu', padding='same'))
model.add(MaxPooling2D((2,2), strides=(2,2)))
model.add(BatchNormalization())

# Block 2
model.add(Conv2D(128, (3,3), activation='relu', padding='same'))
model.add(Conv2D(128, (3,3), activation='relu', padding='same'))
model.add(MaxPooling2D((2,2), strides=(2,2)))
model.add(BatchNormalization())

# Block 3
model.add(Conv2D(256, (3,3), activation='relu', padding='same'))
model.add(Conv2D(256, (3,3), activation='relu', padding='same'))
model.add(Conv2D(256, (3,3), activation='relu', padding='same'))
model.add(MaxPooling2D((2,2), strides=(2,2)))
model.add(BatchNormalization())

# Block 4
model.add(Conv2D(512, (3,3), activation='relu', padding='same'))
model.add(Conv2D(512, (3,3), activation='relu', padding='same'))
model.add(Conv2D(512, (3,3), activation='relu', padding='same'))
model.add(MaxPooling2D((2,2), strides=(2,2)))
model.add(BatchNormalization())

# Block 5
model.add(Conv2D(512, (3,3), activation='relu', padding='same'))
model.add(Conv2D(512, (3,3), activation='relu', padding='same'))
model.add(Conv2D(512, (3,3), activation='relu', padding='same'))
model.add(MaxPooling2D((2,2), strides=(2,2)))
model.add(BatchNormalization())

# Fully Connected layers
model.add(Flatten())
model.add(Dense(256, activation='relu'))
model.add(Dropout(0.5))
model.add(Dense(128, activation='relu'))
model.add(Dense(1, activation='sigmoid'))

# 모델 컴파일 및 훈련
model.compile(optimizer='adam', loss='binary_crossentropy', metrics=['accuracy'])
results = model.fit_generator(train_image_gen, validation_data=valid_image_gen, epochs=50, steps_per_epoch=SPE, validation_steps=VS)

early_stop = EarlyStopping(monitor='val_loss', patience=20, verbose=1, restore_best_weights=True)

model.save("CNN__model.h5")