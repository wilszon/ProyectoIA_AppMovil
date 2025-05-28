from flask import Flask, request, jsonify
import joblib
import pandas as pd

app = Flask(__name__)

scaler = joblib.load("scaler.pkl")
model = joblib.load("xgboost_model.pkl")

@app.route("/predict", methods=["POST"])
def predict():
    data = request.json
    df = pd.DataFrame([{
        "Study_Time": data["study_time"],
        "Number_of_Failures": data["number_of_failures"],
        "Wants_Higher_Education": 1 if data["wants_higher_education"] else 0,
        "Grade_1": data["grade_1"] * 4,
        "Grade_2": data["grade_2"] * 4,
    }])
    
    scaled = scaler.transform(df)
    prediction = model.predict(scaled)[0]
    return jsonify({"prediction": int(prediction)})

if __name__ == "__main__":
    app.run(debug=True)
