package br.com.abruzzo.WebFluxReactiveAPI.config;

import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.PutItemOutcome;
import com.amazonaws.services.dynamodbv2.document.Table;


public class HeroPopulateData {


        public static void main(String[] args) throws Exception {

            DynamoDB dynamoDB = ConnectionFactory.obterDynamoDB();
            Table table = dynamoDB.getTable(ParametrosConfig.TABLENAME.getValue());

            Item hero = new Item()
                    .withPrimaryKey("id", "2")
                    .withString("name", "Mulher Maravilha")
                    .withString("universe", "dc comics")
                    .withNumber("films", 2);

            Item hero2 = new Item()
                    .withPrimaryKey("id", "3")
                    .withString("name", "Viuva negra")
                    .withString("universe", "marvel")
                    .withNumber("films", 2);

            Item hero3 = new Item()
                    .withPrimaryKey("id", "4")
                    .withString("name", "Capita marvel")
                    .withString("universe", "marvel")
                    .withNumber("films", 2);

            PutItemOutcome outcome1 = table.putItem(hero);
            PutItemOutcome outcome2 = table.putItem(hero2);
            PutItemOutcome outcome3 = table.putItem(hero3);

        }

    }
