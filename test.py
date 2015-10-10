from bs4 import BeautifulSoup
import math
import sqlite3
f = open('sample.html')
soup = BeautifulSoup(f.read())
words = [ str(x.contents[0]) for x in soup.select(".qWord") ]
#print words
meaning = [ str(x.text.split('\n')[0]) for x in soup.select(".qDef") ]
example = [ " ".join(x.text.split('\n')[1:]) for x in soup.select(".qDef") ]
conn = sqlite3.connect('test.db')
c = conn.cursor()
c.execute("create table vocab (id int primary key, word text, meaning text, example text, deck int, level int)")
c.execute("create table deck_info (id int primary key, size int, unanswered int, novice int, intermediate int, expert int)")
n = len(words)
for x in range(len(words)):
	c.execute("insert into vocab values(?,?,?,?,?,?)",(x, words[x], meaning[x], example[x], (x/50), 26 ))
num = int(math.ceil( float(len(words)) / 50 ))
for x in range(num):
        y = min((x+1)*50,n) - x*50
        c.execute("insert into deck_info values(?,?,?,?,?,?)",(x, y,y,0,0,0)); 
conn.commit()
conn.close()
