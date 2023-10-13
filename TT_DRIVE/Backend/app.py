from flask import Flask,redirect, url_for, request,jsonify
import pymongo
import shortuuid
from flask_cors import CORS
import datetime
from dotenv import dotenv_values
config = dotenv_values(".env")


cluster = pymongo.MongoClient(config["ATLAS_URI"])
db = cluster["ttd"]
collection = db['text24']

app = Flask(__name__)
CORS(app)
@app.route("/")
def hme():
  d = "its working finally!"
  return d

# @app.route("/insert/<data>")
@app.route("/insert",methods = ['POST'])
def insert():
  if request.method == 'POST':
      text = request.form['text']
      flag=1
      uid="uid"
      while(flag):
        uid = shortuuid.ShortUUID().random(length=4)
        uid = uid.lower()
        if(collection.find_one({"_id":uid}) is None):
           flag=0
      ct = datetime.datetime.now()
      collection.insert_one({"_id":uid, "text":text,"timestamp": ct})
      return uid
  # text = data
  # uid = shortuuid.ShortUUID().random(length=4)
  # ct = datetime.datetime.now()
  # collection.insert_one({"_id":uid, "text":text,"timestamp": ct})
  # url = "ttdrive.vercel.app/"+uid
  # return url

# @app.route('/success/<uid>')
# def success(uid):
#    url = "ttdrive.vercel.app/"+uid
#    return url

@app.route('/find/<uid>')

def data(uid):
  text = collection.find_one({"_id":uid})
  return text["text"]

# @app.route("/visit")
# def visit():
#   visitNo+=1
#   return visitNo
@app.route('/<code>')
def scode(code):
    return redirect(url_for('data', uid = code))

if __name__ == "__main__":
  app.run(host="0.0.0.0",debug=True)
