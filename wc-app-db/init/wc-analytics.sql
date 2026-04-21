-- =============================
-- WORLD CUP ANALYTICS SCHEMA
-- =============================


-- =============================
-- Champion Stats
-- =============================

CREATE TABLE champion_stats (

                                id SERIAL PRIMARY KEY,

                                country TEXT UNIQUE NOT NULL,

                                wins INTEGER NOT NULL

);


-- =============================
-- Champion Era Stats
-- =============================

CREATE TABLE champion_era_stats (

                                    id SERIAL PRIMARY KEY,

                                    era TEXT NOT NULL,

                                    country TEXT NOT NULL,

                                    wins INTEGER NOT NULL,

                                    UNIQUE (era, country)

);


-- =============================
-- Runner-up Stats
-- =============================

CREATE TABLE runnerup_stats (

                                id SERIAL PRIMARY KEY,

                                country TEXT UNIQUE NOT NULL,

                                runnerup_count INTEGER NOT NULL

);


-- =============================
-- Runner-up Without Titles
-- =============================

CREATE TABLE runnerup_without_titles (

                                         id SERIAL PRIMARY KEY,

                                         country TEXT UNIQUE NOT NULL

);


-- =============================
-- Third Place Stats
-- =============================

CREATE TABLE thirdplace_stats (

                                  id SERIAL PRIMARY KEY,

                                  country TEXT UNIQUE NOT NULL,

                                  thirdplace_count INTEGER NOT NULL

);


-- =============================
-- Top 3 Stats
-- =============================

CREATE TABLE top3_stats (

                            id SERIAL PRIMARY KEY,

                            country TEXT UNIQUE NOT NULL,

                            appearances INTEGER NOT NULL

);


-- =============================
-- Top 3 Consistency Stats
-- =============================

CREATE TABLE top3_consistency_stats (

                                        id SERIAL PRIMARY KEY,

                                        country TEXT UNIQUE NOT NULL,

                                        consistency_score DOUBLE PRECISION NOT NULL

);


-- =============================
-- Host Metrics
-- =============================

CREATE TABLE host_metrics (

                              id SERIAL PRIMARY KEY,

                              metric TEXT UNIQUE NOT NULL,

                              value DOUBLE PRECISION NOT NULL

);


-- =============================
-- Host Top 3 by Country
-- =============================

CREATE TABLE host_top3_by_country (

                                      id SERIAL PRIMARY KEY,

                                      country TEXT UNIQUE NOT NULL,

                                      top3_count INTEGER NOT NULL

);


-- =============================
-- Scoring Correlation Stats
-- =============================

CREATE TABLE scoring_corr_stats (

                                    id SERIAL PRIMARY KEY,

                                    metric TEXT UNIQUE NOT NULL,

                                    value DOUBLE PRECISION NOT NULL

);


-- =============================
-- Scoring by Era
-- =============================

CREATE TABLE scoring_by_era (

                                id SERIAL PRIMARY KEY,

                                era TEXT UNIQUE NOT NULL,

                                avg_goals_per_game DOUBLE PRECISION NOT NULL

);


-- =============================
-- Tournament Structure by Era
-- =============================

CREATE TABLE structure_by_era (

                                  id SERIAL PRIMARY KEY,

                                  era TEXT UNIQUE NOT NULL,

                                  avg_teams DOUBLE PRECISION NOT NULL,

                                  avg_matches DOUBLE PRECISION NOT NULL,

                                  avg_goals DOUBLE PRECISION NOT NULL

);


-- =============================
-- Teams Growth Over Time
-- =============================

CREATE TABLE teams_growth (

                              id SERIAL PRIMARY KEY,

                              year INTEGER UNIQUE NOT NULL,

                              teams INTEGER NOT NULL

);


-- =============================
-- Matches Growth Over Time
-- =============================

CREATE TABLE matches_growth (

                                id SERIAL PRIMARY KEY,

                                year INTEGER UNIQUE NOT NULL,

                                matches INTEGER NOT NULL

);