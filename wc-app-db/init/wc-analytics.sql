-- =========================
-- CHAMPIONS
-- =========================

CREATE TABLE champion_stats (
                                id SERIAL PRIMARY KEY,
                                country TEXT UNIQUE NOT NULL,
                                wins INTEGER NOT NULL
);

CREATE TABLE champion_era_stats (
                                    id SERIAL PRIMARY KEY,
                                    era TEXT NOT NULL,
                                    country TEXT NOT NULL,
                                    wins INTEGER NOT NULL,
                                    UNIQUE (era, country)
);

-- =========================
-- RUNNER-UPS & THIRD PLACE
-- =========================

CREATE TABLE runnerup_stats (
                                id SERIAL PRIMARY KEY,
                                country TEXT UNIQUE NOT NULL,
                                runnerup_count INTEGER NOT NULL
);

CREATE TABLE runnerup_without_titles (
                                         id SERIAL PRIMARY KEY,
                                         country TEXT UNIQUE NOT NULL
);

CREATE TABLE thirdplace_stats (
                                  id SERIAL PRIMARY KEY,
                                  country TEXT UNIQUE NOT NULL,
                                  thirdplace_count INTEGER NOT NULL
);

-- =========================
-- TOP 3
-- =========================

CREATE TABLE top3_stats (
                            id SERIAL PRIMARY KEY,
                            country TEXT UNIQUE NOT NULL,
                            appearances INTEGER NOT NULL
);

CREATE TABLE top3_consistency_stats (
                                        id SERIAL PRIMARY KEY,
                                        country TEXT UNIQUE NOT NULL,
                                        consistency_score DOUBLE PRECISION NOT NULL
);

-- =========================
-- HOST ANALYTICS
-- =========================

CREATE TABLE host_metrics (
                              id SERIAL PRIMARY KEY,
                              metric TEXT UNIQUE NOT NULL,
                              value DOUBLE PRECISION NOT NULL
);

CREATE TABLE host_top3_by_country (
                                      id SERIAL PRIMARY KEY,
                                      country TEXT UNIQUE NOT NULL,
                                      top3_count INTEGER NOT NULL
);

-- =========================
-- SCORING ANALYTICS
-- =========================

CREATE TABLE scoring_corr_stats (
                                    id SERIAL PRIMARY KEY,
                                    metric TEXT UNIQUE NOT NULL,
                                    value DOUBLE PRECISION NOT NULL
);

CREATE TABLE scoring_by_era (
                                id SERIAL PRIMARY KEY,
                                era TEXT UNIQUE NOT NULL,
                                avg_goals_per_game DOUBLE PRECISION NOT NULL
);

-- =========================
-- TOURNAMENT STRUCTURE
-- =========================

CREATE TABLE structure_by_era (
                                  id SERIAL PRIMARY KEY,
                                  era TEXT UNIQUE NOT NULL,
                                  avg_teams DOUBLE PRECISION NOT NULL,
                                  avg_matches DOUBLE PRECISION NOT NULL,
                                  avg_goals DOUBLE PRECISION NOT NULL
);

CREATE TABLE teams_growth (
                              id SERIAL PRIMARY KEY,
                              year INTEGER UNIQUE NOT NULL,
                              teams INTEGER NOT NULL
);

CREATE TABLE matches_growth (
                                id SERIAL PRIMARY KEY,
                                year INTEGER UNIQUE NOT NULL,
                                matches INTEGER NOT NULL
);

CREATE TABLE worldcup_summary_stats (
                                        id SERIAL PRIMARY KEY,

                                        total_editions INTEGER NOT NULL,
                                        total_champions INTEGER NOT NULL,
                                        total_hosts INTEGER NOT NULL,

                                        highest_avg_goals DOUBLE PRECISION NOT NULL,
                                        highest_avg_goals_year INTEGER NOT NULL
);