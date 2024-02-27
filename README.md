# govee-java-api

This project provides a java wrapper over the Govee v1.0 API (https://developer.govee.com/ and https://govee-public.s3.amazonaws.com/developer-docs/GoveeDeveloperAPIReference.pdf).

It can be used to interface with govee lights or appliances, including the ability to subscribe to events from devices via MQTT.

### Obtaining an API Key

[See Govee getting starting document](https://developer.govee.com/docs/getting-started)

### Usage

Get an instance of the GoveeAPI object like `GoveeApi.getInstance("API_KEY")`. This object can be stored and reused without fear of leaving open connections.
This object has methods for interacting with devices and appliances.

### Examples

1. Listing devices

```
List<GoveeDevice> devices = GoveeApi.getInstance(API_KEY).getDevices().getData();
```

2. Getting status of a device:

```
GoveeDevice device = GoveeApi.getInstance(API_KEY)
				.getDeviceStatus(TEST_SKU, TEST_DEVICEID)
				.getDevice();
```

### Running tests

1. Create an `application.properties` file in `/src/test/resources/application.properties`
2. Add your api key into the file like `govee_api_key=[my secret api key]`
3. To run tests with a specific device, you'll need the device model number and the device id (available by calling the `getDevices` api)
   - Add the model like: `govee_device_sku=[device model]`
   - Add the deviceId like: `govee_device_id=[device id]`
4. Run

## Installing

This library is available as a GitHub package and can be installed by adding to your `pom.xml`

```
<dependency>
  <groupId>com.bigboxer23</groupId>
  <artifactId>govee-java-api</artifactId>
  <version>1.0.0</version>
</dependency>
```

Since it is a GitHub package, GitHub requires maven authenticate as a valid user to fetch from their package repository.

Instructions:
1. Create `~/.m2/settings.xml` if it does not exist
2. Create a GitHub personal access token if you do not have one (`settings` -> `developer settings` -> `tokens classic`)
3. In below example need to update `username` to your own GitHub username.  Update `password` to include your GitHub
access token created in the previous step
4. Example file:

```
<settings xmlns="http://maven.apache.org/SETTINGS/1.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" 
xsi:schemaLocation="http://maven.apache.org/SETTINGS/1.0.0 https://maven.apache.org/xsd/settings-1.0.0.xsd">
  <localRepository />
  <interactiveMode />
  <usePluginRegistry />
  <offline />
  <pluginGroups />
  <servers>
    <server>
      <id>github</id>
      <username>[my username]</username>
      <password>[my GH Access Token]</password>
    </server>
  </servers>
  <mirrors />
  <proxies />
</settings>
```

