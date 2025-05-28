import requests

url = "http://127.0.0.1:5000/predict"

data = {
    "study_time": 2,
    "number_of_failures": 1,
    "wants_higher_education": True,
    "grade_1": 3,
    "grade_2": 1
}

response = requests.post(url, json=data)
print(response.json())
