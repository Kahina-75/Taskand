# -*- coding: utf-8 -*-
import flask, json, requests
from flask import jsonify
app = flask.Flask(__name__)
app.config["DEBUG"] = True

taches ={
	"nom": ["cuisine"],
	"Description": ["cuisiner pasta"],
	"Niveau_diffculte": [3],
	"niveau_priorite": [2],
	"nombre_taches_filles": [3]
          }

@app.route('/', methods=['GET'])
def home():
    return "<h1>Home page.</p>"
 
@app.route('/toto', methods=['GET'])
def show_entries():
    """Affiche les données entrées dans la page principale"""
 
    toto = json.dumps(taches, ensure_ascii=False)
    return toto

# envoie identifiant du fichier taches.json
@app.route('/resend_json_data', methods=['GET'])
def resend_json_data():

    tach = 'identifiant_url'
    return tach


# Sending data to Taskand android app 
@app.route('/jsontaskB', methods=['GET'])
def jsontaskB():

       
      return jsonify(taches)
    
 




app.run()
