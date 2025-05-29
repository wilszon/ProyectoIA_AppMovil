# 🎓 Predicción de Deserción Universitaria

# Objetivo
Desarrollar una ap movil para android que basado en informacion proporcionada por el usuario, la envia a un servidor backend con una api llamada Flask
procese los datos obtenidos mediante un modelo preentrenado para predecir si va a desertar de la carrera o por el contrario continuara sus estudios universitarios y muestre 
la prediccion en la aplicacion movil.







# Estructura de la APP
Requisitos

# Backend y Aplicación Web

Railway para servidor y backend

Flask==3.1.1 ,

streamlit==1.45.1 ,

gunicorn==23.0.0  

# Ciencia de Datos y Machine Learning
pandas==2.2.3

numpy==2.2.6

scikit-learn==1.6.1

xgboost==3.0.2

scipy==1.15.3

joblib==1.5.1

# Visualización de Datos
altair==5.5.0

pydeck==0.9.1

# Serialización y formatos de datos
pyarrow==20.0.0

protobuf==6.31.0


# Este proyecto utiliza herramientas de ciencia de datos, aprendizaje automático y visualización web. 

bash

pip install -r requirements.txt



#Dependencias de kotlin Utilizadas

implementation("com.squareup.retrofit2:retrofit:2.9.0")

implementation("com.squareup.retrofit2:converter-gson:2.9.0")

implementation("androidx.compose.ui:ui-text:1.5.0")

# Configuracion de Proyecto en RailWay

## 🌐 Despliegue

Proceso de despliegue del backend


![Despliegue en Railway](https://github.com/amgito1648/clase-inteligencia-artificial/raw/main/Capturas/Railway.png) 

 Seleccionar enviroment.
 
 ![Despliegue en Railway](https://github.com/amgito1648/clase-inteligencia-artificial/raw/main/Capturas/railway1.jpeg) 


El backend fue desplegado exitosamente en Railway y está disponible en:

`https://web-production-7a7f2.up.railway.app`

![Despliegue en Railway](https://github.com/amgito1648/clase-inteligencia-artificial/raw/main/Capturas/proyecto_ia_railway.png) 


Evidencia de las peticiones que se han realizado al servidor

![image](https://github.com/user-attachments/assets/d776c164-d9de-4516-bec7-1b6e9146a555)




## 📱 Capturas de la Aplicación

Pantalla principal donde se ingresan los datos del estudiante y se realiza la predicción:

![Captura de pantalla de la app](https://github.com/amgito1648/clase-inteligencia-artificial/raw/main/Capturas/predict0.jpeg)



---

Ingresando los datos solicitados  y según el entrenamiento nos predice si la persona va a Abandonar la carrera.

![Captura de pantalla de la app](https://github.com/amgito1648/clase-inteligencia-artificial/raw/main/Capturas/predict2.jpeg)


---
 Tambien  predice si la persona va a continuar la carrera.

![Captura de pantalla de la app](https://github.com/amgito1648/clase-inteligencia-artificial/raw/main/Capturas/predict3.jpeg)




## 👨‍💻 Autores del Proyecto

- **Wilson Suarez**
- **Mateo Sandoval**
- **Cristian Cala**




