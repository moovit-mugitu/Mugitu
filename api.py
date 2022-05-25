from flask import Flask, jsonify, request
from flask_restful import Api, Resource, reqparse
import pickle
import numpy as np
import pandas as pd
import json

app = Flask(__name__)
api = Api(app)

# Create parser for the payload data
parser = reqparse.RequestParser()
parser.add_argument('data')

# Define how the api will respond to the post requests
class IrisClassifier(Resource):
    def post(self):
        json_data = request.get_json(force=True)
        args = parser.parse_args()
        X = json_data['data']
        id = json_data['id']
        print("El valor de X es "+X)
        filename = 'models/' + id + '.pckl'
        # Load model
        with open(filename, 'rb') as f:
            model = pickle.load(f)

        future_date = pd.DataFrame({'ds':[X]})
        prediction = model.predict(future_date)
        resultado = prediction[['yhat']]
        print(resultado)
        yath = resultado['yhat'].iloc[0]
        return jsonify(yath)

api.add_resource(IrisClassifier, '/')

if __name__ == '__main__':
    app.run(host='0.0.0.0',port=80)

