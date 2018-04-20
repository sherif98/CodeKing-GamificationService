package com.codeking.gamificationservice.domain;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;

public enum Badge {


    FIRST_PROBLEM_SOLVED {
        @Override
        public Predicate<Map<ProgrammingLanguage, Set<String>>> shouldEarnBadge() {
            return (Map<ProgrammingLanguage, Set<String>> problemsCount) ->
                    problemsCount.values().stream().anyMatch(problemIds -> problemIds.size() > 0);
        }
    },
    SOLVED_10_PROBLEMS {
        @Override
        public Predicate<Map<ProgrammingLanguage, Set<String>>> shouldEarnBadge() {
            return (Map<ProgrammingLanguage, Set<String>> problemsCount) ->
                    problemsCount.values().stream().flatMap(Collection::stream).distinct().count() >= 10;

        }
    },
    SOLVED_100_PROBLEMS {
        @Override
        public Predicate<Map<ProgrammingLanguage, Set<String>>> shouldEarnBadge() {
            return (Map<ProgrammingLanguage, Set<String>> problemsCount) ->
                    problemsCount.values().stream().flatMap(Collection::stream).distinct().count() >= 10;
        }
    },
    JAVA_ELITE {
        @Override
        public Predicate<Map<ProgrammingLanguage, Set<String>>> shouldEarnBadge() {
            return (Map<ProgrammingLanguage, Set<String>> problemsCount) ->
                    problemsCount.get(ProgrammingLanguage.JAVA).size() >= 100;
        }
    };

    public abstract Predicate<Map<ProgrammingLanguage, Set<String>>> shouldEarnBadge();

}
