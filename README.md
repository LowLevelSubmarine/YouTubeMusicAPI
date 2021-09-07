[![](https://jitpack.io/v/LowLevelSubmarine/YouTubeMusicAPI.svg)](https://jitpack.io/#LowLevelSubmarine/YouTubeMusicAPI)

# YouTube Music API (in-official)
This API is made to scrape data from youtube music while avoiding their api services.
The API will work fast, due to the fact that its not loading the real site and is instead accessing the data directly via the youtube music data endpoint.

## Easy to use!
Just create a new *YTMA* object and start with the *search(String)* method ;)

## Download

**Gradle**
```gradle
dependencies {
   implementation 'com.github.LowLevelSubmarine:ytma:v1.1.1'
}

repositories {
    maven { url 'https://jitpack.io' }
}
```

**Maven**
```xml
<dependency>
    <groupId>com.github.LowLevelSubmarine</groupId>
    <artifactId>ytma</artifactId>
    <version>v1.1.1</version>
</dependency>
```
```xml
<repositories>
  <repository>
      <id>jitpack.io</id>
      <url>https://jitpack.io</url>
  </repository>
</repositories>
```
