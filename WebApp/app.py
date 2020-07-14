from flask import Flask, render_template, request
import requests
#from elasticsearch import Elasticsearch
import json

app = Flask(__name__)
#es = Elasticsearch('10.0.1.10', port=9200)


@app.route('/')
def home():
    users = requests.get('http://localhost:8080/api/v1/users')
    usersjson = users.json()
    print(len(usersjson))
    return render_template('index.html', users=usersjson)

@app.route('/search', methods=['GET', 'POST'])
def search_page():

    username= request.args.get('user', default='') 
    print('user selected ' + username)
    users = requests.get('http://localhost:8080/api/v1/users')
    usersjson = users.json()
    activeuser = get_active_user(username)
    

    return render_template('search.html', user=activeuser)

@app.route('/search/results', methods=['GET', 'POST'])
def search_request():
    search_term = request.form["input"]
    print("search term " + search_term)
    params = {'user': "", 'account_search': False, 'hashtag_search': False, 'personalized': False }
    
    #replace space with + to make the search url like
    search_term = search_term.replace(" ", "+")

    url_get_request = 'http://localhost:8080/api/v1/search/'

    if request.form.get('personalized_search'):
        params['personalized'] = True
        #params['user'] = request.form.get['username']
        print("PERSONALIZED")
            
   # print("costumsearch : " + request.form.get('costumsearch'))

    if request.form.get('retweet') == "retweetsort" :
    #    url_get_request = url_get_request + "user/"
        params['account_search'] = True
        print("ACCOUNT SEARCH")


    elif request.form.get('costumsearch') == "hashtag" :
     #   url_get_request = url_get_request + "hashtag/" 
        params['hashtag_search'] = True
        print("HASHTAG SEARCH")
        


    username= request.args.get('user', default='')  
    activeuser = get_active_user(username)
    params['user'] = username


    print(url_get_request + search_term)
    res = requests.get( url_get_request + search_term, params)
    jsonres = res.json()


    print(res)
    print(len(jsonres))
    total = len(jsonres)

    return render_template('results.html', res=jsonres, total=total, user=activeuser)


def get_active_user(username):

    users = requests.get('http://localhost:8080/api/v1/users')
    usersjson = users.json()
    activeuser = usersjson[9]
    for el in usersjson:
        if el["name"] == username:
            activeuser = el
            break
    return activeuser


if __name__ == '__main__':
    app.secret_key = 'mysecret'
    app.run(host='0.0.0.0', port=5000, debug=True)
