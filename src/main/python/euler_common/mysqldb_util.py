import mysql.connector
import pymysql.cursors


# Establish connection
conn = pymysql.connect(
    host="localhost",
    user="nam",
    password="nampassword",
    database="euler",
    cursorclass=pymysql.cursors.DictCursor
)