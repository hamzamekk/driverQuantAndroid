/**
 * Sample React Native App
 * https://github.com/facebook/react-native
 *
 * @format
 * @flow strict-local
 */

import React from 'react';
import {
  SafeAreaView,
  StyleSheet,
  ScrollView,
  View,
  Text,
  StatusBar,
  NativeModules,
  TouchableOpacity,
  TextInput,
  ToastAndroid,
} from 'react-native';

import PlayServices from 'react-native-play-services';
import {requestMultiple, PERMISSIONS} from 'react-native-permissions';

import {
  Header,
  LearnMoreLinks,
  Colors,
  DebugInstructions,
  ReloadInstructions,
} from 'react-native/Libraries/NewAppScreen';

const {DriveQuant} = NativeModules;

class App extends React.Component {
  state = {
    email: 'juanisco7@gmail.com',
  };

  async componentDidMount() {
    const status = await PlayServices.checkPlayServicesStatus();

    switch (status) {
      case PlayServices.GooglePlayServicesStatus.GMS_DISABLED:
        PlayServices.goToSetting();
        break;
      case PlayServices.GooglePlayServicesStatus.GMS_NEED_UPDATE:
        PlayServices.goToMarket();
      // handle anything.
      default:
        break;
    }
    console.log(status);

    requestMultiple([
      PERMISSIONS.ANDROID.ACCESS_BACKGROUND_LOCATION,
      PERMISSIONS.ANDROID.ACCESS_COARSE_LOCATION,
      PERMISSIONS.ANDROID.ACCESS_FINE_LOCATION,
      PERMISSIONS.ANDROID.ACTIVITY_RECOGNITION,
    ]).then((statuses) => {
      // console.log(
      //   'ACCESS_BACKGROUND_LOCATION',
      //   statuses[PERMISSIONS.ANDROID.ACCESS_BACKGROUND_LOCATION],
      // );
      // console.log(
      //   'ACCESS_COARSE_LOCATION',
      //   statuses[PERMISSIONS.ANDROID.ACCESS_COARSE_LOCATION],
      // );
      // console.log('ACCESS_FINE_LOCATION', [
      //   PERMISSIONS.ANDROID.ACCESS_FINE_LOCATION,
      // ]);
      // console.log(
      //   'ACTIVITY_RECOGNITION',
      //   statuses[PERMISSIONS.ANDROID.ACTIVITY_RECOGNITION],
      // );
    });
  }

  render() {
    return (
      <>
        <StatusBar barStyle="dark-content" />
        {/* <View> */}
        <TextInput
          placeholder="Add Email Here"
          style={{borderWidth: 1}}
          value={this.state.email}
          onChangeText={(email) => this.setState({email})}
        />
        {/* </View> */}

        <ScrollView>
          <TouchableOpacity
            style={{
              height: 40,
              borderWidth: 1,
              justifyContent: 'center',
              alignItems: 'center',
              marginVertical: 20,
              backgroundColor: 'green',
            }}
            onPress={() => DriveQuant.activateAutoStart(true)}>
            <Text>activateAutoStart</Text>
          </TouchableOpacity>
          {/* <TouchableOpacity
            style={{
              height: 40,
              borderWidth: 1,
              justifyContent: 'center',
              alignItems: 'center',
              marginVertical: 20,
              backgroundColor: 'green',
            }}
            onPress={() => DriveQuant.activateAutoStart(true)}>
            <Text>activateAutoStart</Text>
          </TouchableOpacity> */}
          <TouchableOpacity
            style={{
              height: 40,
              borderWidth: 1,
              justifyContent: 'center',
              alignItems: 'center',
              marginVertical: 20,
              backgroundColor: 'green',
            }}
            onPress={() => DriveQuant.init()}>
            <Text>initialize</Text>
          </TouchableOpacity>
          <TouchableOpacity
            style={{
              height: 40,
              borderWidth: 1,
              justifyContent: 'center',
              alignItems: 'center',
              marginVertical: 20,
              backgroundColor: 'green',
            }}
            onPress={() => {
              DriveQuant.receiveEmail('juanisco7@gmail.com');
            }}>
            <Text>setEmail</Text>
          </TouchableOpacity>

          <TouchableOpacity
            style={{
              height: 40,
              borderWidth: 1,
              justifyContent: 'center',
              alignItems: 'center',
              marginVertical: 20,
              backgroundColor: 'green',
            }}
            onPress={() => {
              DriveQuant.startTrip();
            }}>
            <Text>start Trip</Text>
          </TouchableOpacity>

          <TouchableOpacity
            style={{
              height: 40,
              borderWidth: 1,
              justifyContent: 'center',
              alignItems: 'center',
              marginVertical: 20,
              backgroundColor: 'green',
            }}
            onPress={() => DriveQuant.stopTrip()}>
            <Text>stop Trip</Text>
          </TouchableOpacity>

          <TouchableOpacity
            style={{
              height: 40,
              borderWidth: 1,
              justifyContent: 'center',
              alignItems: 'center',
              marginVertical: 20,
              backgroundColor: 'green',
            }}
            onPress={() =>
              DriveQuant.isConfigured((val) =>
                ToastAndroid.show(`is Configured ${val}`, ToastAndroid.SHORT),
              )
            }>
            <Text>isConfigured</Text>
          </TouchableOpacity>

          <TouchableOpacity
            style={{
              height: 40,
              borderWidth: 1,
              justifyContent: 'center',
              alignItems: 'center',
              marginVertical: 20,
              backgroundColor: 'green',
            }}
            onPress={() => DriveQuant.reset()}>
            <Text>reset</Text>
          </TouchableOpacity>
        </ScrollView>
      </>
    );
  }
}

const styles = StyleSheet.create({
  scrollView: {
    backgroundColor: Colors.lighter,
  },
  engine: {
    position: 'absolute',
    right: 0,
  },
  body: {
    backgroundColor: Colors.white,
  },
  sectionContainer: {
    marginTop: 32,
    paddingHorizontal: 24,
  },
  sectionTitle: {
    fontSize: 24,
    fontWeight: '600',
    color: Colors.black,
  },
  sectionDescription: {
    marginTop: 8,
    fontSize: 18,
    fontWeight: '400',
    color: Colors.dark,
  },
  highlight: {
    fontWeight: '700',
  },
  footer: {
    color: Colors.dark,
    fontSize: 12,
    fontWeight: '600',
    padding: 4,
    paddingRight: 12,
    textAlign: 'right',
  },
});

export default App;
