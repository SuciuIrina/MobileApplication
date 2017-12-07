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

import BookDetailComponent from "./BookDetailComponent";
import AddBookComponent from "./AddBookComponent";

export default class App extends Component<{}> {
    constructor(props) {
        super(props);

        this.getBooksForFlatList = this.getBooksForFlatList.bind(this);
        this.getBookListElements = this.getBookListElements.bind(this);
        this.setDetailView = this.setDetailView.bind(this);
        this.getBookDetailComponent = this.getBookDetailComponent.bind(this);
        this.setBookListView = this.setBookListView.bind(this);
        this.handleUpdate = this.handleUpdate.bind(this);
        this.setAddBookView = this.setAddBookView.bind(this);
        this.addNewBook = this.addNewBook.bind(this);
        this.deleteAllBooks = this.deleteAllBooks.bind(this);
        this.deleteBook = this.deleteBook.bind(this);
        this.alertDialogShow = this.alertDialogShow.bind(this);
        this.getUsersHarcodated=this.getUsersHarcodated.bind(this);
        this.getWishListHarcodated=this.getWishListHarcodated.bind(this);

        users=this.getUsersHarcodated();
        whislist=this.getWishListHarcodated();

        AsyncStorage.setItem('users',JSON.stringify(users));
        AsyncStorage.setItem('whislist',JSON.stringify(whislist));


        viewElement = this.getBookListElements([]);
        this.state = {books: [], viewElement: viewElement, whishlist:whislist}

        const secondThis = this;
        AsyncStorage.getItem('books').then(v => {
            if (v == undefined) {
                AsyncStorage.setItem('books', JSON.stringify([]));
                AsyncStorage.setItem('counter', '0');
            } else {
                viewElement = secondThis.getBookListElements(JSON.parse(v));
                secondThis.setState({books: JSON.parse(v), viewElement: viewElement});
            }
        });
    }


    getBooksForFlatList(books) {
        books.map(x => x.key = x.id);
        return books;
    }

    alertDialogShow() {
        Alert.alert(
            'Confirm your choice',
            'Are you sure you want to remove all books?',
            [
                {text: 'YES', onPress: () => this.deleteAllBooks()},
                {text: 'NO', onPress: () => console.log('Cancel Pressed'), style: 'cancel'},
            ],
            {cancelable: false}
        )
    }

    getUsersHarcodated() {
        return
        [

            {id: 1, username: "ana", email: "anamaria@yahoo.com"},
            {id: 2, username: "andrei", email: "andreipopescu87@yahoo.com"},
            {id: 3, username: "alex", email: "alex@yahoo.com"},
            {id: 4, username: "ionescu", email: "ionescu@yahoo.com"},
            {id: 5, username: "florescu", email: "florescu@yahoo.com"},
            {id: 6, username: "simona", email: "simona@yahoo.com"},
            {id: 7, username: "irina", email: "suciuirinacj@yahoo.com"},
        ]
    }

    getWishListHarcodated(){
        return
        [
            {usernameId:1,bookId:1,date:"2017-12-01"},
            {usernameId:2,bookId:1,date:"2017-12-01"},
            {usernameId:3,bookId:1,date:"2017-12-02"},
            {usernameId:4,bookId:1,date:"2017-12-03"},
            {usernameId:5,bookId:1,date:"2017-12-04"},
            {usernameId:6,bookId:1,date:"2017-12-04"},
            {usernameId:7,bookId:1,date:"2017-12-04"},
            {usernameId:1,bookId:2,date:"2017-12-01"},
            {usernameId:2,bookId:2,date:"2017-12-01"},
            {usernameId:3,bookId:2,date:"2017-12-01"},
            {usernameId:4,bookId:2,date:"2017-12-02"},
            {usernameId:5,bookId:2,date:"2017-12-03"},
            {usernameId:6,bookId:2,date:"2017-12-06"},
            {usernameId:7,bookId:2,date:"2017-12-06"},
            {usernameId:1,bookId:3,date:"2017-12-03"},
            {usernameId:2,bookId:3,date:"2017-12-03"},
            {usernameId:3,bookId:3,date:"2017-12-03"},
            {usernameId:4,bookId:3,date:"2017-12-04"},
            {usernameId:5,bookId:3,date:"2017-12-05"},
            {usernameId:6,bookId:3,date:"2017-12-05"},
            {usernameId:7,bookId:3,date:"2017-12-06"}

        ]}



    getBookListElements(books) {
        myBooks = this.getBooksForFlatList(books);
        return (
            <View style={styles.mainView}>
                <FlatList
                    style={styles.listView}
                    data={myBooks}
                    renderItem={({item}) =>
                        <TouchableHighlight onPress={() => {
                            this.setDetailView(item.id)
                        }} underlayColor="azure">

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
                            title={"Add book"}
                            color="#841584"
                            onPress={() => this.setAddBookView()}
                        />
                    </View>
                    <View style={{width: 130, padding: 5}}>
                        <Button
                            style={styles.buttonStyle}
                            title={"Delete all"}
                            color="#841584"
                            onPress={() => this.alertDialogShow()}
                        />
                    </View>
                </View>
            </View>
        );

    }

    addNewBook(book) {
        var books = this.state.books.slice();
        AsyncStorage.getItem('counter').then(v => {
            var newCounter = parseInt(v) + 1;
            book.id = newCounter;
            books.push(book);
            AsyncStorage.setItem('counter', "" + newCounter);
            AsyncStorage.setItem('books', JSON.stringify(books));
            this.setState({books: books, viewElement: this.getBookListElements(books)});
        });

    }

    deleteBook(bookId) {
        let filteredBooks = this.state.books;
        filteredBooks = filteredBooks.filter(element => element.id != bookId);
        this.setState({books: filteredBooks, viewElement: this.getBookListElements(filteredBooks)});
        AsyncStorage.setItem('books', JSON.stringify(filteredBooks));

    }

    setAddBookView() {
        newElement = <AddBookComponent
            addBook={this.addNewBook}
            onComeBack={() => {
                this.setBookListView()
            }}
        />;
        this.setState({viewElement: newElement});
    }

    setDetailView(bookId) {
        book = this.state.books.find(b => b.id === bookId);
        newElement = this.getBookDetailComponent(book);
        this.setState({viewElement: newElement});
    }


    getBookDetailComponent(book) {
        return <BookDetailComponent
            wishlist={this.state.whishlist}
            book={book}
            onUpdate={this.handleUpdate}
            onDelete={this.deleteBook}
            onComeBack={() => {
                this.setBookListView()
            }}
        />;
    }

    setBookListView() {
        this.setState({viewElement: this.getBookListElements(this.state.books)});
    }

    handleUpdate(book) {
        newBooks = this.state.books;
        newBooks[newBooks.findIndex(el => el.id === book.id)] = book;
        AsyncStorage.setItem('books', JSON.stringify(newBooks));
        this.setState({books: newBooks, viewElement: this.getBookListElements(this.state.books)});
    }

    deleteAllBooks() {
        AsyncStorage.setItem('books', JSON.stringify([]));
        AsyncStorage.setItem('counter', '0');
        this.setState({books: [], viewElement: this.getBookListElements([])});
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
