# Video-Stream
Video-Stream is a video-streaming application similar to Youtube. 

Technology Stack Used:

Frontend: Angular JS                                                                                                                                                   

Backend: Java 8, SpringBoot

Database: MongoDB

Cloud: AWS (S3 Service) - to store videos and thumbnail.

Tools Used: Intellij, Visual Studio Code, Auth0 for Single-Sign-On Authentication, Git  


![image](https://user-images.githubusercontent.com/74978517/225093206-ca6abbe9-f333-43f2-bba3-72c94f667809.png)


The website starts with the page where you have to login first. Without logging in, videos won't be visible to the user.
Login can be done using Single-Sign-On which is implemented using auth0.

![image](https://user-images.githubusercontent.com/74978517/225095038-5e110206-da46-46b9-9d0e-71ce78a286d3.png)


Once Signed in, you will be able to see all the videos uploaded by all the users. You can view any of the videos by clicking on them. The videos are display using video-cards which consists of - The thumbnail image, Video title, Video description and the no. of views for each video received.

![image](https://user-images.githubusercontent.com/74978517/225096881-6cde4bf5-02c4-4c61-9ae3-c5c895c52e2e.png)

Click on any video to view that video
![image](https://user-images.githubusercontent.com/74978517/225104797-4f659c47-b4de-4c5c-babd-9e351f809e5a.png)

The video-details page consists of the following details:-

          -the video, -video-title, -no. of views received, 

          -the like and the dislike button, -video-description
          
          -the name of the user who uploaded the video
          
          -the tags, -subscribe button
          
          -and the comment section

![image](https://user-images.githubusercontent.com/74978517/225105742-a7ddbc98-44da-4c74-b1f1-29a26db2b527.png)


In the comment section you can share your thoughts regarding the video and they will be displayed below.


![image](https://user-images.githubusercontent.com/74978517/225106107-d250a330-14da-4ed4-91de-a49a06ef1411.png)

Inside the subscriptions tab, you will able to see all the videos that have been uploaded by the users to whom you have subscribed.


![image](https://user-images.githubusercontent.com/74978517/225106722-2cbfd99a-ba1f-4341-84a4-9c1bc075ae0b.png)

Inside the Liked Videos tab, you can see all the videos that you have liked.


![image](https://user-images.githubusercontent.com/74978517/225107053-4d0c7c30-a629-4cf5-8fd6-3df50f9c821c.png)

Inside the History tab, all the videos that have been viewed by you till now will be shown.


![image](https://user-images.githubusercontent.com/74978517/225107419-c996e380-2adc-466f-83b5-b7294ccb6c52.png)

On the right side of the navbar, there is a video-icon. Click on it to upload a new video.


![image](https://user-images.githubusercontent.com/74978517/225108013-5e28a82e-dd9c-469d-85ee-a379800fc188.png)


Upload the video file and click on the button to upload it to AWS S3 bucket. Videos and thumbnails are not uploaded to the database. Instead, the are stored in S3 bucket and are accessed from there.


![image](https://user-images.githubusercontent.com/74978517/225108648-1189d34c-f597-43f9-9be8-267505fe5dd7.png)
![image](https://user-images.githubusercontent.com/74978517/225108846-87690298-44d3-4259-a089-813ffc39c57a.png)

Once Video is uploaded to S3 bucket, you are directed to save-video-details page to add the other video-related information.


![image](https://user-images.githubusercontent.com/74978517/225109710-415c4108-47bc-4a23-bea4-0a000f2e60a7.png)


After adding information, click on save. Then upload the thumbnail and click on upload. The thumbnail will be uploaded to AWS S3 bucket.


![image](https://user-images.githubusercontent.com/74978517/225110881-f1f5def8-1559-4c82-a343-8df47884ae52.png)


As you can see the new video is visible now in the home section.


![image](https://user-images.githubusercontent.com/74978517/225111525-db349021-9b76-4bf8-a3ee-33803e5ee9a2.png)


This is the bucket in S3 where the videos and thumbnails are uploaded.






