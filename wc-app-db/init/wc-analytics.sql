CREATE TABLE champion_stats (

    country TEXT PRIMARY KEY,

    wins INTEGER NOT NULL

);

CREATE TABLE champion_era_stats (

    era TEXT NOT NULL,

    country TEXT NOT NULL,

    wins INTEGER NOT NULL,

    PRIMARY KEY (era, country)

);


CREATE TABLE runnerup_stats (

    country TEXT PRIMARY KEY,

    runnerup_count INTEGER NOT NULL

);

CREATE TABLE runnerup_without_titles (

    country TEXT PRIMARY KEY

);


CREATE TABLE thirdplace_stats (

    country TEXT PRIMARY KEY,

    thirdplace_count INTEGER NOT NULL

);


CREATE TABLE top3_stats (

    country TEXT PRIMARY KEY,

    appearances INTEGER NOT NULL

);


CREATE TABLE top3_consistency_stats (

    country TEXT PRIMARY KEY,

    consistency_score DOUBLE PRECISION NOT NULL

);



CREATE TABLE host_metrics (

    metric TEXT PRIMARY KEY,

    value DOUBLE PRECISION NOT NULL

);



CREATE TABLE host_top3_by_country (

    country TEXT PRIMARY KEY,

    top3_count INTEGER NOT NULL

);


CREATE TABLE scoring_corr_stats (

    metric TEXT PRIMARY KEY,

    value DOUBLE PRECISION NOT NULL

);


CREATE TABLE scoring_by_era (

    era TEXT PRIMARY KEY,

    avg_goals_per_game DOUBLE PRECISION NOT NULL

);



CREATE TABLE structure_by_era (

    era TEXT PRIMARY KEY,

    avg_teams DOUBLE PRECISION NOT NULL,

    avg_matches DOUBLE PRECISION NOT NULL,

    avg_goals DOUBLE PRECISION NOT NULL

);


CREATE TABLE teams_growth (

    year INTEGER PRIMARY KEY,

    teams INTEGER NOT NULL

);


CREATE TABLE matches_growth (

    year INTEGER PRIMARY KEY,

    matches INTEGER NOT NULL

);