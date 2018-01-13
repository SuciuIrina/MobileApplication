import * as firebase from "firebase";
import React, {Component} from 'react';
import {Input} from "./components/Input";
import {Button} from "./components/Button";
import App from "./App"
import {
    StyleSheet,
    Text,
    View,
    TextInput,
    ActivityIndicator,
    Alert

} from 'react-native';

export default class LoginComponent extends Component<{}> {

    constructor(props) {
        super(props);

        this.renderCurrentState = this.renderCurrentState.bind(this);
        this.onPressSignIn = this.onPressSignIn.bind(this);
        this.onPressRegister = this.onPressRegister.bind(this);
        this.signOut=this.signOut.bind(this);

        this.state = {
            email: '',
            password: '',
            authenticating: false,
            user: ''
        }
    }

    componentDidMount() {
        this.setState({user:null})
        firebase.auth().onAuthStateChanged((user) => {
            this.setState({
                user: user,
                authenticating: true
            });
        });
    }

    componentWillMount() {
        const firebaseConfig = {
            apiKey: "AIzaSyAc-TFsLb2iyfxSYXahj1CdgVxUiwaIAFw",
            authDomain: "libraryapplicationreact.firebaseapp.com",
            databaseURL: "https://libraryapplicationreact.firebaseio.com",
            projectId: "libraryapplicationreact",
            storageBucket: "libraryapplicationreact.appspot.com",
            messagingSenderId: "511352699114"
        }

        try {
            firebase.initializeApp(firebaseConfig);
        }catch(err){
            
        }

        firebase.auth().signOut();
        firebase.auth().onAuthStateChanged((user) => {
            this.setState({
                user: user,
                authenticating: true
            });
        });
    }

    onPressSignIn() {
        firebase.auth().signInWithEmailAndPassword(this.state.email, this.state.password)
            .catch((error) => {
            const {code, message} = error;
            Alert.alert(
                'Log in failed',
                error.message,
                [
                    {text: 'Go back', onPress: () => console.log('Cancel Pressed'), style: 'cancel'},
                ],
                {cancelable: true}
            )
        });

    }

    onPressRegister() {
        firebase.auth().createUserWithEmailAndPassword(this.state.email, this.state.password)
            .catch((error) => {
            const {code, message} = error;
            Alert.alert(
                'Register failed',
                error.message,
                [
                    {text: 'Go back', onPress: () => console.log('Cancel Pressed'), style: 'cancel'},
                ],
                {cancelable: true}
            )
        });
    }

    renderCurrentState() {
        return (
            <View style={styles.form}>
                <Input
                    placeholder='Enter your email'
                    label='Email'
                    onChangeText={email => this.setState({email: email})}
                    value={this.state.email}
                />
                <Input
                    placeholder='Enter your password'
                    label='Password'
                    secureTextEntry
                    onChangeText={password => this.setState({password: password})}
                    value={this.state.password}
                />

                <Button onPress={() => this.onPressSignIn()}>Log In</Button>
                <Button onPress={() => this.onPressRegister()}>Register</Button>
            </View>
        )

    }

    signOut(){
        firebase.auth().signOut();
    }
    render() {
        if(firebase.auth().currentUser !=null){
            return (
                <App
                    signOut={this.signOut}
                />
            )
        }else{
            return(
                <View style={styles.container}>
                    {this.renderCurrentState()}
                </View>
            )
        }
    }


}

const styles = StyleSheet.create({
    container: {
        flex: 1,
        padding: 20,
        alignItems: 'center',
        justifyContent: 'center',
        flexDirection: 'row'
    },
    form: {
        flex: 1
    }
});