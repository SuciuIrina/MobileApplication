import * as firebase from 'firebase';
const firebaseConfig = {
    apiKey: "AIzaSyAc-TFsLb2iyfxSYXahj1CdgVxUiwaIAFw",
    authDomain: "libraryapplicationreact.firebaseapp.com",
    databaseURL: "https://libraryapplicationreact.firebaseio.com",
    projectId: "libraryapplicationreact",
    storageBucket: "libraryapplicationreact.appspot.com",
    messagingSenderId: "511352699114"
};
const firebaseApp = firebase.initializeApp(firebaseConfig);
export {firebaseApp};