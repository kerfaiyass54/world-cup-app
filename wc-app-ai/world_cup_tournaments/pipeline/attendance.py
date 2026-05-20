def highest_attendance(df):

    row = df.loc[df["Attendance"].idxmax()]

    return {
        "Year": row["Year"],
        "Host": row["Host"],
        "Attendance": row["Attendance"]
    }


def highest_average_attendance(df):

    row = df.loc[df["AttendanceAvg"].idxmax()]

    return {
        "Year": row["Year"],
        "Host": row["Host"],
        "AttendanceAvg": row["AttendanceAvg"]
    }


def attendance_by_year(df):

    return df[[
        "Year",
        "Attendance"
    ]]


def average_attendance_by_year(df):

    return df[[
        "Year",
        "AttendanceAvg"
    ]]


def attendance_by_host(df):

    return df[[
        "Host",
        "Attendance"
    ]]


def average_attendance_by_host(df):

    return df[[
        "Host",
        "AttendanceAvg"
    ]]