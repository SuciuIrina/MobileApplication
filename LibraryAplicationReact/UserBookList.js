import React, {Component} from 'react';

import {
    AsyncStorage,
    Button,
    Platform, ScrollView,
    StyleSheet,
    Text,
    View,
    Alert, TouchableHighlight, FlatList
} from 'react-native';
import {firebaseApp} from './components/FirebaseConfig';
import LoginComponent from "./LoginComponent";

export default class UserBookList extends Component<{}> {
    constructor(props) {
        super(props);
        this.getBooksForFlatList = this.getBooksForFlatList.bind(this);
        this.signOut = this.signOut.bind(this);
        this.getBookListElement = this.getBookListElement.bind(this);


        viewElement = this.getBookListElement([]);
        this.state = {books: [], viewElement: viewElement}

    }

    componentDidMount() {
        firebaseApp.database().ref().child('books').on('value', (childSnapshot) => {
            var updatedBookList = [];

            childSnapshot.forEach(element => {
                updatedBookList.push(element.val());
            })

            console.log(updatedBookList);
            newViewElement = this.getBookListElement(updatedBookList);
            this.setState({books: updatedBookList, viewElement: newViewElement});
        });
    }

    componentDidUnMount() {
        firebaseApp.database().ref().child('books').off('value');
    }

    getBooksForFlatList(books) {
        books.map(x => x.key = x.id);
        return books;
    }

    signOut() {
        firebaseApp.auth().signOut();
        newElement = <LoginComponent/>;
        this.setState({viewElement: newElement});
    }

    getBookListElement(books) {
        myBooks = this.getBooksForFlatList(books);
        return (
            <View style={styles.mainView}>
                <FlatList
                    style={styles.listView}
                    data={myBooks}
                    renderItem={({item}) =>
                        <TouchableHighlight  underlayColor="azure">

                            <View style={styles.listItemView}>
                                <Text style={styles.bigBlack}>
                                    {" Title: " + item.title + "\n Author: " + item.author + "\n Rating: " + item.rating}
                                </Text>
                            </View>

                        </TouchableHighlight>
                    }
                />

                <View style={styles.buttonContainer}>
                    <View style={{width: 130, padding: 5}}>
                        <Button
                            style={styles.buttonStyle}
                            title={"Sign out"}
                            color="#841584"
                            onPress={() => this.signOut()}
                        />
                    </View>

                </View>
            </View>
        )
    }

    render() {
        return this.state.books === null ? null : (
            this.state.viewElement
        );
    }
}

const styles = StyleSheet.create({
    mainView: {
        flex: 1
    },
    listView: {
        padding: 10,
        flex: 0.8,
        backgroundColor: 'white',
    },
    listItemView: {
        padding: 5
    },
    bigBlack: {
        fontSize: 15,
        fontWeight: 'bold',
    },
    buttonContainer: {
        flex: 0.2,
        flexDirection: 'row',
        alignItems: 'center',
        justifyContent: 'center',
    },
    buttonStyle: {
        flex: 1,
    },
});