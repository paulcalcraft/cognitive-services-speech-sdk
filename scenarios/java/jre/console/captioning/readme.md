# Captioning app with the Speech SDK for Java

In this sample app for Windows, macOS, or Linux, you create captions from audio input using the speech-to-text capability of the Speech SDK for Java.

## Prerequisites

- Azure subscription - [Create one for free](https://azure.microsoft.com/free/cognitive-services)
- <a href="https://ms.portal.azure.com/#create/Microsoft.CognitiveServicesSpeechServices"  title="Create a Speech resource"  target="_blank">Create a Speech resource</a> in the Azure portal. You can use the free pricing tier (`F0`) to try the service, and upgrade later to a paid tier for production.
- Get the resource key and region. After your Speech resource is deployed, select **Go to resource** to view and manage keys. For more information about Cognitive Services resources, see [Get the keys for your resource](https://docs.microsoft.com/azure/cognitive-services/cognitive-services-apis-create-account#get-the-keys-for-your-resource).
- Install the [Java 8 or 11 JRE/JDK](https://java.com/en/download/help/download_options.html).
- Install [Apache Maven](https://maven.apache.org/install.html).
- On Ubuntu or Debian, run the following commands to install the required packages:
```sh
sudo apt-get update
sudo apt-get install libssl1.0.0 libasound2
```
If libssl1.0.0 is not available, install libssl1.0.x (where x is greater than 0) or libssl1.1 instead.
- On RHEL or CentOS, run the following commands to install the required packages:
```sh
sudo yum update
sudo yum install alsa-lib java-1.8.0-openjdk-devel openssl
```
See also [how to configure RHEL/CentOS 7 for Speech SDK](https://docs.microsoft.com/azure/cognitive-services/speech-service/how-to-configure-rhel-centos-7).

## Set up the environment

The Speech SDK is available as a [Maven package](https://mvnrepository.com/artifact/com.microsoft.cognitiveservices.speech/client-sdk). You install the Speech SDK in the next section of this article, but first check the [platform-specific installation instructions](https://docs.microsoft.com/azure/cognitive-services/speech-service/quickstarts/setup-platform) for any more requirements.

> **NOTE**
> By downloading the Microsoft Cognitive Services Speech SDK, you acknowledge its [license agreement](https://aka.ms/csspeech/license201809).

If you wish to create captions from compressed audio files such as MP4 files, you must also install [GStreamer](https://docs.microsoft.com/azure/cognitive-services/speech-service/how-to-use-codec-compressed-audio-input-streams).

> **NOTE**
> - When you install GStreamer on a Windows computer, you must create environment variables named `GST_PLUGIN_PATH` and `GSTREAMER_ROOT_X86_64`, as well as adding `C:\gstreamer\1.0\msvc_x86_64\bin` to your PATH environment variable (see the previous link for more information). Be sure to make these changes to your system environment variables, not your user environment variables. After making changes to your system environment variables, you must reboot your computer for the changes to take effect.
> - If one or more of these conditions is not met, you might receive the error `SPXERR_GSTREAMER_NOT_FOUND_ERROR` when you try to use GStreamer with the captioning scenario app.

## Build and run the sample

1. Download or copy the [scenarios/java/jre/console/captioning/](https://github.com/Azure-Samples/cognitive-services-speech-sdk/tree/master/scenarios/java/jre/console/captioning/) sample files from GitHub into a local directory.
1. Open a command prompt and cd to the `scenarios/java/jre/console/captioning` folder in the downloaded GitHub sample files.
1. Create a new `pom.xml` file in the `scenarios/java/jre/console/captioning` folder, and copy and paste the following into it:
    ```xml
    <project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
        <modelVersion>4.0.0</modelVersion>
        <groupId>com.microsoft.cognitiveservices.speech.samples</groupId>
        <artifactId>quickstart-eclipse</artifactId>
        <version>1.0.0-SNAPSHOT</version>
        <build>
            <sourceDirectory>src</sourceDirectory>
            <plugins>
            <plugin>
                <artifactId>maven-compiler-plugin</artifactId>
                <version>3.7.0</version>
                <configuration>
                <source>1.8</source>
                <target>1.8</target>
                </configuration>
            </plugin>
            </plugins>
        </build>
        <repositories>
            <repository>
            <id>maven-cognitiveservices-speech</id>
            <name>Microsoft Cognitive Services Speech Maven Repository</name>
            <url>https://csspeechstorage.blob.core.windows.net/maven/</url>
            </repository>
        </repositories>
        <dependencies>
            <dependency>
            <groupId>com.microsoft.cognitiveservices.speech</groupId>
            <artifactId>client-sdk</artifactId>
            <version>1.21.0</version>
            </dependency>
        </dependencies>
    </project>
    ```
1. Install the Speech SDK and dependencies.
    ```console
    mvn clean dependency:copy-dependencies
    ```
1. Compile the project files. 
    ```console
    javac Captioning.java -cp ".;target\dependency\*"
    ```
1. Run the application with your preferred command line arguments. See [usage and arguments](#usage-and-arguments) for the available options. Here is an example:
    ```console
    java -cp ".;target\dependency\*" Captioning --key YourSubscriptionKey --region YourServiceRegion --input c:\caption\caption.this.mp4 --format any --output c:\caption\caption.output.txt - --srt --recognizing --threshold 5 --profanity mask --phrases "Contoso;Jessie;Rehaan"
    ```
    Replace `YourSubscriptionKey` with your Speech resource key, and replace `YourServiceRegion` with your Speech resource [region](https://aka.ms/csspeech/region), such as `westus` or `northeurope`. Make sure that the paths specified by `--input` and `--output` are valid. Otherwise you must change the paths.
1. The output file with complete captions is written to `c:\caption\caption.output.txt`. Intermediate results are shown in the console:
    ```console
    00:00:00,180 --> 00:00:01,600
    Welcome to
    
    00:00:00,180 --> 00:00:01,820
    Welcome to applied
    
    00:00:00,180 --> 00:00:02,420
    Welcome to applied mathematics
    
    00:00:00,180 --> 00:00:02,930
    Welcome to applied mathematics course
    
    00:00:00,180 --> 00:00:03,100
    Welcome to applied Mathematics course 2
    
    00:00:00,180 --> 00:00:03,230
    Welcome to applied Mathematics course 201.
    ```

## Usage and arguments

Usage: `captioning --key <key> --region <region> --input <input file>`

Connection options include:

- `--key`: Your Speech resource key. 
- `--region REGION`: Your Speech resource region. Examples: `westus`, `northeurope`

Input options include:

- `--input FILE`: Input audio from file. The default input is the microphone. 
- `--format FORMAT`: Use compressed audio format. Valid only with `--input`. Valid values are `alaw`, `any`, `flac`, `mp3`, `mulaw`, and `ogg_opus`. The default value is `any`. You should not use this option with a `wav` input file. Using a compressed audio format is not available with the JavaScript captioning sample. For compressed audio files such as MP4, install GStreamer and see [How to use compressed input audio](https://docs.microsoft.com/azure/cognitive-services/speech-service/how-to-use-codec-compressed-audio-input-streams).

Language options include:

- `--languages LANG1,LANG2`: Enable language identification for specified languages. For example: `en-US,ja-JP`. This option is only available with the C++, C#, and Python captioning samples. For more information, see [Language identification](https://docs.microsoft.com/azure/cognitive-services/speech-service/language-identification).

Recognition options include:

- `--recognizing`: Output `Recognizing` event results. The default output is `Recognized` event results only. These are always written to the console, never to an output file. The `--quiet` option overrides this. For more information, see [Get speech recognition results](https://docs.microsoft.com/azure/cognitive-services/speech-service/get-speech-recognition-results).

Accuracy options include:

- `--phrases PHRASE1;PHRASE2`: You can specify a list of phrases to be recognized, such as `Contoso;Jessie;Rehaan`. For more information, see [Improve recognition with phrase list](https://docs.microsoft.com/azure/cognitive-services/speech-service/improve-accuracy-phrase-list).

Output options include:

- `--help`: Show this help and stop
- `--output FILE`: Output captions to the specified `file`. This flag is required.
- `--srt`: Output captions in SRT (SubRip Text) format. The default format is WebVTT (Web Video Text Tracks). For more information about SRT and WebVTT caption file formats, see [Caption output format](https://docs.microsoft.com/azure/cognitive-services/speech-service/captioning-concepts#caption-output-format).
- `--quiet`: Suppress console output, except errors.
- `--profanity OPTION`: Valid values: raw, remove, mask. For more information, see [Profanity filter](https://docs.microsoft.com/azure/cognitive-services/speech-service/captioning-concepts#profanity-filter) concepts.
- `--threshold NUMBER`: Set stable partial result threshold. The default value with this code example is `3`. For more information, see [Get partial results](https://docs.microsoft.com/azure/cognitive-services/speech-service/captioning-concepts.#get-partial-results) concepts.

## Clean up resources

You can use the [Azure portal](https://docs.microsoft.com/azure/cognitive-services/cognitive-services-apis-create-account#clean-up-resources) or [Azure Command Line Interface (CLI)](https://docs.microsoft.com/azure/cognitive-services/cognitive-services-apis-create-account-cli#clean-up-resources) to remove the Speech resource you created.

## References

* [Captioning concepts](https://docs.microsoft.com/azure/cognitive-services/speech-service/captioning-concepts)
* [Captioning quickstart](https://docs.microsoft.com/azure/cognitive-services/speech-service/captioning-quickstart)
* [Speech SDK API reference for Java](https://docs.microsoft.com/java/api/com.microsoft.cognitiveservices.speech?view=azure-java-stable)