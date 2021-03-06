package com.stg.emailpoller;

import com.stg.emailpoller.dto.UserPhotoDto;
import com.stg.emailpoller.repository.DataSourceFactory;
import org.skife.jdbi.v2.DBI;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

/**
 * Main driver.
 */
public class EmailPoller {
    private DataSource dataSource;
    private DBI dbi;
    private String emailAddress;
    private String password;
    private Properties configProps;

    public EmailPoller() {
        ReadConfig readConfig = new ReadConfig("config.properties");
        dataSource = DataSourceFactory.getMySQLDataSource();

        try {
            configProps = readConfig.getPropValues();
        } catch (IOException e) {
            e.printStackTrace();
        }
        emailAddress = configProps.getProperty("gmail.login");
        password = configProps.getProperty("gmail.password");
    }

    public static void main(String[] args) {
        EmailPoller emailPoller = new EmailPoller();
        emailPoller.init();
        emailPoller.execute();
        emailPoller.egress();
    }

    public void init() {

        System.out.println("Hello Email Poller Application.");
        dbi = new DBI(dataSource);
    }

    public void execute() {
//        UserDao userDao = dbi.open(UserDao.class);
//        PhotoDao photoDao = dbi.open(PhotoDao.class);

        String host = "pop.gmail.com";// change accordingly
        String mailStoreType = "pop3";
        String username =
                "stgtest.user@stgconsulting.com";// change accordingly
        String password = "Test_User_Password0";// change accordingly
//        String username =
//                "dqromney@gmail.com";// change accordingly
//        String password = "Tgtb5501";// change accordingly
        // Call method fetch
        List<UserPhotoDto> userPhotoDtoList = FetchEmail.fetch(host, mailStoreType, username, password);
        for (UserPhotoDto item : userPhotoDtoList) {
            System.out.println(item);
//            User user = item.getUser();
//            Photo photo = item.getPhoto();
//            userDao.insert(user.getEmail(), user.getName());
//            Long userId = userDao.findNameByEmail(user.getEmail());
//            photoDao.insert(photo.getSubject(), photo.getText(), photo.getImageUrl(), userId);
        }

//        Email email = new Email();
//        try {
//            List<UserPhotoDto> userPhotoDtoList = email.read(emailAddress, password);
//            for (UserPhotoDto item : userPhotoDtoList) {
//                System.out.println(item);
//                User user = item.getUser();
//                Photo photo = item.getPhoto();
//                userDao.insert(user.getEmail(), user.getName());
//                Long userId = userDao.findNameByEmail(user.getEmail());
//                photoDao.insert(photo.getSubject(), photo.getText(), photo.getImageUrl(), userId);
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }

    public void egress() {
        System.out.println("Exiting Email Poller Application.");
    }
}
