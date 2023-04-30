# this file attempted to serialize the data to be the best fit for the postgres when I convert the excel to its final format

import re
from csv import reader
import psycopg2
from psycopg2 import pool


with open('RawCSVDataFromExcelHcr.csv', encoding='utf-8') as f:
    cases = reader(f)

    nums = []
    for case in cases:
        #cleaning up the variaty of the not available formats
        case = ['Null' if atr == ''  else atr for atr in case ]
        case = ['Null' if atr == ' '  else atr for atr in case ]
        case = ['Null' if atr == 'na' else atr for atr in case]
        case = ['Null' if atr == 'NA' else atr for atr in case]
        case = ['Null' if atr == 'N.A' else atr for atr in case]
        case = ['Null' if atr == 'n.a' else atr for atr in case]
        case = ['Null' if atr == 'n.a.' else atr for atr in case]
        #print(case)
        new_c = []
        for i in range(len(case)):


                #cleaning up the various kind of date formats
                if re.match(r'\d\d\d\d[.|/]\d{1,2}[.|/]\d{1,2}', case[i]):
                    if case[i].count('.'):
                        t= case[i].split('.')
                        t = f"{t[0]}-{t[1]}-{t[2]}"
                        new_c.append(t)



                    elif case[i].count('/'):
                        t= case[i].split('/')
                        t = f"{t[0]}-{t[1]}-{t[2]}"
                        new_c.append(t)


                elif re.match(r'\d{1,2}[.|/|-]\d{1,2}[.|/|-]\d\d\d\d', case[i]):
                    if case[i].count('.'):
                        t= case[i].split('.')
                        t = f"{t[-1]}-{t[1]}-{t[0]}"
                        new_c.append(t)


                    elif case[i].count('/'):
                        t= case[i].split('/')
                        t = f"{t[-1]}-{t[1]}-{t[0]}"
                        new_c.append(t)


                    elif case[i].count('-'):
                        t= case[i].split('-')
                        t = f"{t[-1]}-{t[1]}-{t[0]}"
                        new_c.append(t)

                   # looking for the not complete dates in the databses. they are marked with year.hh.nn (Hónap=month, Nap=day )
                elif re.match(r'\d{4}\.(\d{2}|[a-zA-Z]{2})\.([a-zA-Z]{2})', case[i]):
                    date_mod = case[i].split('.')
                    for u in range(len(date_mod)):
                        #looking for the ones where the month is there
                        if re.match(r'.\d{2}.',  date_mod[u]):
                            new_date = f'{date_mod[0]}-{date_mod[1]}-01'
                            case[-2] = f"{case[-2]}, Date modified from {case[i]} to {new_date} 0001." #0001 = dtae modified by us, code for easier auto filtering
                            #looking for the month if it is still not valid
                            if re.match(r'[a-z]',  date_mod[1]):
                                date_mod = new_date.split('-')
                                new_date = f'{date_mod[0]}-01-{date_mod[2]}'
                                case[-2] = f"{case[-2]}, Date modified from {case[i]} to {new_date} 0001." #0001 = dtae modified by us, code for easier auto filtering
                                new_c.append(new_date)

                            else:
                                new_c.append(new_date)




                else:
                    new_c.append(case[i])

        #clearing out the the fileds which is intteger in the postgres so the null actually became a null
        for i in range(len(new_c)):
            if new_c[4] == 'Null':
                    new_c[4] = None
            elif new_c[8] == 'Null':
                    new_c[8] = None
            elif new_c[15] == 'Null':
                    new_c[15] = None
            elif new_c[24] == 'Null':
                    new_c[24] = None
            elif new_c[25] == 'Null':
                    new_c[25] = None
            elif new_c[26] == 'Null':
                    new_c[26] = None
            elif new_c[27] == 'Null':
                    new_c[27] = None
            elif new_c[31] == 'Null':
                new_c[31] = ''
         # float values in the ransom filed, not goood
        if new_c[27] != None:

            if re.match(r'\d{1,6}\.\d{1,2}', new_c[27]):
                ransome = new_c[27].split('.')
                new_c[27] = f"{ransome[0]}{ransome[1]}"
        if new_c[26]:
            pass



        # first remove the incorrect input from the filed 25 field
        if new_c[25] != None:
            date_exp = re.compile(r'\d\d\d\d-\d{1,2}-\d{1,2}')
            date = date_exp.search(new_c[25])
            if not date:
                comment1 = new_c[25]
                new_c[25] = None
                #check if the field 24 has wrong vlaue too. if it does then move along, comment will be set from field 24 so we dont need to add the 25's content
                if new_c[24] != None:
                    if re.match(r'\d\d\d\d-\d{1,2}-\d{1,2}', new_c[24]):
                        pass
                    else:
                        new_c[31] += f', {new_c[25]} 0003 ' #0002 code for cases when accusation was dropped


        if new_c[24] != None:
             date_exp = re.compile(r'\d\d\d\d-\d{1,2}-\d{1,2}')
             date = date_exp.search(new_c[24])
             if not date:
                 new_c[31] += f', {new_c[24]} 0002' #0002 code for cases when accusation was dropped
                 new_c[24] = None

                 #print(new_c[31])
         #0005 duplicate id   
        #print(f'{new_c[24]}, {new_c[25]},  {new_c[31]}')

        if new_c[8]:
            date_exp = re.compile(r'\d{4}-\d{1,2}-\d{1,2}')
            date_of_birth = date_exp.search(new_c[8])
            if not date_of_birth:

                new_c[31] += f', születlési napnál:  {new_c[8]} 0003' #0003 the person did not know his/her birthdate
                new_c[8] = None

        if len(new_c[5]) > 10:
            if new_c[5] != 'Null' or None:
                print(f'testalkat: {new_c[5]}')

        if len(new_c[3]) > 1:

            if new_c[3] != 'Null' or None:
                print(f'nem: {new_c[3]} {new_c[1]}  ')

        if len(new_c[12]) > 3:
            if new_c[12] != 'Null' or None:
                print(f'vallas: {new_c[12]}')

        if len(new_c[20]) > 3:
            if new_c[20] != 'Null' or None:
                print(f'iras: {new_c[20]}')

        if len(new_c[6]) > 1:
            if new_c[6] != 'Null' or None:
                print(f'fog: {new_c[6]}')

        if len(new_c[29]) > 10:
            if new_c[29] != 'Null' or None:
                print(f'eset fok: {new_c[29]}')

        if len(new_c[30]) > 10:
            if new_c[30] != 'Null' or None:
                print(f'bunfok: {new_c[30]}')

        # if len(new_c[12]) > 3:
        #     print(f'vallas: {new_c[12]}')

        # if len(new_c[20]) > 3:
        #     print(f'iras: {new_c[20]}')

        # if len(new_c[6]) > 1:
        #     print(f'fog: {new_c[6]}')

        print(f'{new_c} is the next to insert ')

        # if new_c[26] != None:
        #     if re.match(r'\d{1,6}\.\d{1,2}', new_c[26]):
        #             print(f'{new_c[1]} inserted row  27: {new_c[24]} 25: {new_c[25]} 26: {new_c[26]} 27: {new_c[27]} ')
        data_pool = psycopg2.pool.SimpleConnectionPool(1,20,
                                    dbname='hdc',
                                    host='localhost',
                                    user = 'hdcroot',
                                    password = 'testPass123')
        conn = data_pool.getconn()
        with conn.cursor() as cursor:
            dbquery = "INSERT INTO captives_data (volume, id, name, sex, height, build, dentition, special_peculiarities, date_of_birth, place_of_birth, place_of_residence, residence, religion, childhood_status, marital_status, number_of_children, occupation, occupation_2, occupation_3, military_service, literacy, education, criminal_history, crime, sentence_begins, sentence_expires, prison_term_day, ransome, associates, degree_of_crime, degree_of_punishment, notes, arrest_site, username)  VALUES(%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,'hdcroot');"
            cursor.execute(dbquery, tuple(new_c))
        conn.commit()
        print(f'{new_c} is inserted ')
