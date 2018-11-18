package hello.repositories.impl;

import hello.model.SeekerDescription;
import hello.Service.SeekerDescriptionProvider;
import hello.repositories.SeekerDescriptionRepository;
import org.springframework.stereotype.Repository;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.*;

@Repository
public class MultiPropertiesRepository implements SeekerDescriptionRepository {
    private ArrayList<String> propertyFileSource = new ArrayList<>();
    private SeekerDescription seekerDescription = null;
    @Override
    public SeekerDescription getSeekerDescription(List<String> filePaths){
        ArrayList<ReadingPropertiesThread> threadPool = new ArrayList<>();
        if (filePaths.size()>0) {
        propertyFileSource.addAll(filePaths);
        seekerDescription = new SeekerDescription();
            propertyFileSource.forEach((filePath) -> {
                //вызов потоков
                threadPool.add((new ReadingPropertiesThread(filePath,filePath)));
            });
            threadPool.forEach((thread)->{
                thread.start();
                System.out.println("Старт потока " + thread.getName());
            });
            threadPool.forEach((thread)->{
                if (thread.isAlive()) {
                    try {
                        thread.join();
                        System.out.println("Ждем поток "+thread.getName());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });

        }

        return seekerDescription;
    }
    private class ReadingPropertiesThread extends Thread{
        private String filePath;

        public ReadingPropertiesThread(String name, String filePath) {
            super(name);
            this.filePath = filePath;
        }

        @Override
        public void run() {
            try(FileInputStream fis = new FileInputStream(filePath)) {
                Properties property = new Properties();
                property.load(fis);
                Arrays.stream(KEYS).forEach((key)->{
                    //не самая лучшая реализация, но оставлю на рефакторинг, если будет время
                    if (property.containsKey(key)) {
                        switch (key){
                            case "FIO":
                                seekerDescription.setFio(getPropertyByKey(key,property).get(0));
                                break;
                            case "BirthDate":
                                seekerDescription.setBirthDate(getPropertyByKey(key,property).get(0));
                                break;
                            case "TelNumber":
                                seekerDescription.setTelNumber(getPropertyByKey(key,property));
                                break;
                            case "E-Mail": seekerDescription.seteMail(getPropertyByKey(key,property));
                                break;
                            case "Target":
                                seekerDescription.setTarget(getPropertyByKey(key,property));
                                break;
                            case "Experience":
                                seekerDescription.setExperience(getPropertyByKey(key,property));
                                break;
                            case "Education":
                                seekerDescription.setEducation(getPropertyByKey(key,property));
                                break;
                            case "AdditionalEducation":
                                seekerDescription.setAdditionalEducation(getPropertyByKey(key,property));
                                break;
                            case "Skills":
                                seekerDescription.setSkills(getPropertyByKey(key,property));
                                break;
                            case "CodeExample":
                                seekerDescription.setCodeExample(getPropertyByKey(key,property));
                                break;
                            case "ImageSrc":
                                seekerDescription.setImgSrc(getPropertyByKey(key,property));
                                break;
                             default:
                                 break;
                        }

                    } else System.out.println("Ключ " + key + " отсутствует в файле "+filePath+".");
                });
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                System.out.println("Property File "+filePath+" не найден");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        private ArrayList<String> getPropertyByKey(String key, Properties property){
            ArrayList<String> fieldContentList = new ArrayList<>();
            String fieldContent;
            try {
                fieldContent = new String(property.getProperty(key).getBytes("ISO8859-1"),"CP1251");
            } catch (UnsupportedEncodingException e) {
               fieldContent = "Неверная кодировка";
            } catch (NullPointerException e) {
                fieldContent = "Поле не задано";
            }
            if (fieldContent.contains("\n")) {
                fieldContentList = new ArrayList<>(Arrays.asList(fieldContent.split("\n")));
                return fieldContentList;
            } else if (fieldContent.contains(":")) {
                //todo: #UPGRADE сделать сортировку - забыл про нее
                fieldContentList.addAll(Arrays.asList(fieldContent.split(",")));
                return fieldContentList;
            }
                else {
                fieldContentList.add(fieldContent);
                return fieldContentList;
            }
        }

    }
}
