Appointment Service for Health Care management System

How to use this API (design):

### Get all appointments

Endpoint:
`GET` <a href="http://localhost:8080/helthcare/appointments/">
http://localhost:8080/helthcare/appointments/</a>

Headers:
`{"Content-Type": "application/json", "token": token}`


Output:

```
[
    {
        "appointId": 1,
        "createdAt": "2020-04-28Z",
        "date": "2020-04-17Z",
        "doctor": {
            "address": "Female",
            "age": 28,
            "gender": "Colombo",
            "name": "Sanuri",
            "user_id": 4,
            "doctorId": 1,
            "specialization": "Surgeon"
        },
        "hospital": {
            "address": "Uyanwatta, Matara",
            "hospitalId": 1,
            "location": "Matara",
            "name": "Matara Main",
            "type": "Main"
        },
        "patient": {
            "address": "Female",
            "age": 32,
            "gender": "Colombo",
            "name": "Namini",
            "user_id": 2,
            "disease": "Dermatologists",
            "hospitalId": 1,
            "patientId": 1
        },
        "time": "2020-04-17Z",
        "userId": 1
    }
]
```

### Get a user

`GET` http://localhost:8080/user-service/users/1

Headers:
`{"Content-Type": "application/json", "token": token}`

### Add new appointments

Endpoint:
`POST` <a href="http://localhost:8080/helthcare/appointments/">
http://localhost:8080/helthcare/appointments/</a>

Headers:
`{"Content-Type": "application/json", "token": token}`

Body:

```
{
    "patientId": 1,
    "doctorId": 1,
    "hospitalId": 1,
    "userId": 5,
    "date": "2010-05-11",
    "time": "2012-05-21"
}
```

Output:

`1` - Success
`0` - Failed


### Update appointments

Endpoint:
`PUT` <a href="http://localhost:8080/helthcare/appointments/1">
http://localhost:8080/helthcare/appointments/</a>

Headers:
`{"Content-Type": "application/json", "token": token}`

Body:

```
{
    "doctorId": 1,
    "hospitalId": 1,
    "date": "2010-05-11",
    "time": "2012-05-21"
}
```

Output:

`1` - Success
`0` - Failed

### Delete appointments

Endpoint:
`DELETE` <a href="http://localhost:8080/helthcare/appointments/2">
http://localhost:8080/helthcare/appointments/</a>

Headers:
`{"Content-Type": "application/json", "token": token}`

Output:

`1` - Success
`0` - Failed
