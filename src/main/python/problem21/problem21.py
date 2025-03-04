
import matplotlib.pyplot as plt
import euler_common.mysqldb_util as mydb
import sys
import logging
import numpy as np
import io
import uuid

dry_run = False

def test_connect_data():
    conn = mydb.conn
    cursor = conn.cursor()
    cursor.execute("SELECT * FROM problem21")
    result = cursor.fetchall()
    print(result)
    conn.commit()
    cursor.close()
    conn.close()

def main():
    if not dry_run:
        args = sys.argv[1:]
        if len(args) == 0:
            print('No arguments provided')
            sys.exit(1)
        else:    
            conn = mydb.conn
            cursor = conn.cursor() 
            # cursor.execute("SELECT *  FROM problem21 where id in (%s)" % ','.join(['%s'] * len(args)), args)
            query = "SELECT id, upper_bound FROM problem21 WHERE id IN ({})".format(",".join(["%s"] * len(args)))
            print("[Python]query: %s" % query)
            cursor.execute(query, args)
            result = cursor.fetchall()
            dict_problem_id = [{'id': item['id'], 'upper_bound': item['upper_bound']} for item in result]
            for pair in dict_problem_id:
                print('[python]Processing id: ', pair['id'])
                cursor.execute("SELECT * FROM problem21_amicable_pairs where problem21_id = %s", (pair['id'],))
                result = cursor.fetchall()
                if len(result) == 0:
                    print('ID %s has no data' % pair['id'])
                else:
                    print('[python]Data appeared')
                    amicablesA = [item["amicableA"] for item in result]
                    amicablesB = [item["amicableB"] for item in result]
                    img_data = visualize(sorted(amicablesA + amicablesB), pair['upper_bound'])
                    print('[python]Begin to insert in to chart table....')
                    cursor.execute("INSERT INTO problem21_charts (id, problem21_id, image_path, image) VALUES (%s, %s, %s, %s)", (uuid.uuid4(), pair['id'], None, img_data))
                    print('[python]Begin to update in to main table....')
                    cursor.execute("UPDATE problem21 SET generate_image_result = %s  WHERE id = %s", (True, pair['id']))
                    conn.commit()
    else:
        test_connect_data()


def visualize(amicables, upper_bound)->bytes:
    fig, ax = plt.subplots()
    ax.plot(amicables, linestyle='solid')
    img_buffer = io.BytesIO()
    fig.savefig(img_buffer, format="jpeg", bbox_inches="tight")
    img_buffer.seek(0)
    return img_buffer.getvalue()

if __name__ == '__main__':
    main()