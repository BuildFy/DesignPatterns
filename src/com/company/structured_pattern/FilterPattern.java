package com.company.structured_pattern;

import java.util.ArrayList;
import java.util.List;

/**
 * 过滤器模式
 * <p>
 * 过滤器模式（Filter Pattern）或标准模式（Criteria Pattern）是一种设计模式，
 * 这种模式允许开发人员使用不同的标准来过滤一组对象，通过逻辑运算以解耦的方式把它们连接起来。
 * 这种类型的设计模式属于结构型模式，它结合多个标准来获得单一标准。
 * <p>
 * 我们将创建一个 Person 对象、Criteria 接口和实现了该接口的实体类，
 * 来过滤 Person 对象的列表。FilterPattern 类使用 Criteria 对象，
 * 基于各种标准和它们的结合来过滤 Person 对象的列表。
 */
public class FilterPattern {
    public static void main(String[] args) {
        List<Person> persons = new ArrayList<Person>();

        persons.add(new Person("Robert", "Male", "Single"));
        persons.add(new Person("John", "Male", "Married"));
        persons.add(new Person("Laura", "Female", "Married"));
        persons.add(new Person("Diana", "Female", "Single"));
        persons.add(new Person("Mike", "Male", "Single"));
        persons.add(new Person("Bobby", "Male", "Single"));

        Criteria male = new CriteriaMale();
        Criteria female = new CriteriaFemale();
        Criteria single = new CriteriaSingle();
        Criteria singleMale = new AndCriteria(single, male);
        Criteria singleOrFemale = new OrCriteria(single, female);

        System.out.println("Males: ");
        printPersons(male.meetCriteria(persons));

        System.out.println("\nFemales: ");
        printPersons(female.meetCriteria(persons));

        System.out.println("\nSingle Males: ");
        printPersons(singleMale.meetCriteria(persons));

        System.out.println("\nSingle Or Females: ");
        printPersons(singleOrFemale.meetCriteria(persons));
    }

    public static void printPersons(List<Person> persons) {
        for (Person person : persons) {
            System.out.println("Person : [ Name : " + person.getName()
                    + ", Gender : " + person.getGender()
                    + ", Marital Status : " + person.getMaritalStatus()
                    + " ]");
        }
    }

    public static class Person {

        private String name;
        private String gender;
        private String maritalStatus;

        public Person(String name, String gender, String maritalStatus) {
            this.name = name;
            this.gender = gender;
            this.maritalStatus = maritalStatus;
        }

        public String getName() {
            return name;
        }

        public String getGender() {
            return gender;
        }

        public String getMaritalStatus() {
            return maritalStatus;
        }
    }

    public interface Criteria {
        public List<Person> meetCriteria(List<Person> persons);
    }

    public static class CriteriaMale implements Criteria {

        @Override
        public List<Person> meetCriteria(List<Person> persons) {
            List<Person> malePersons = new ArrayList<Person>();
            for (Person person : persons) {
                if (person.getGender().equalsIgnoreCase("MALE")) {
                    malePersons.add(person);
                }
            }
            return malePersons;
        }
    }

    public static class CriteriaFemale implements Criteria {

        @Override
        public List<Person> meetCriteria(List<Person> persons) {
            List<Person> femalePersons = new ArrayList<Person>();
            for (Person person : persons) {
                if (person.getGender().equalsIgnoreCase("FEMALE")) {
                    femalePersons.add(person);
                }
            }
            return femalePersons;
        }
    }

    public static class CriteriaSingle implements Criteria {

        @Override
        public List<Person> meetCriteria(List<Person> persons) {
            List<Person> singlePersons = new ArrayList<Person>();
            for (Person person : persons) {
                if (person.getMaritalStatus().equalsIgnoreCase("SINGLE")) {
                    singlePersons.add(person);
                }
            }
            return singlePersons;
        }
    }

    public static class AndCriteria implements Criteria {

        private Criteria criteria;
        private Criteria otherCriteria;

        public AndCriteria(Criteria criteria, Criteria otherCriteria) {
            this.criteria = criteria;
            this.otherCriteria = otherCriteria;
        }

        @Override
        public List<Person> meetCriteria(List<Person> persons) {
            List<Person> firstCriteriaPersons = criteria.meetCriteria(persons);
            return otherCriteria.meetCriteria(firstCriteriaPersons);
        }
    }

    public static class OrCriteria implements Criteria {

        private Criteria criteria;
        private Criteria otherCriteria;

        public OrCriteria(Criteria criteria, Criteria otherCriteria) {
            this.criteria = criteria;
            this.otherCriteria = otherCriteria;
        }

        @Override
        public List<Person> meetCriteria(List<Person> persons) {
            List<Person> firstCriteriaItems = criteria.meetCriteria(persons);
            List<Person> otherCriteriaItems = otherCriteria.meetCriteria(persons);

            for (Person person : otherCriteriaItems) {
                if (!firstCriteriaItems.contains(person)) {
                    firstCriteriaItems.add(person);
                }
            }
            return firstCriteriaItems;
        }
    }
}
