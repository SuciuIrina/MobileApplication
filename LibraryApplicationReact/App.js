import React, {Component} from 'react';
import {
    Button,
    Platform, ScrollView,
    StyleSheet,
    Text,
    View,
    Alert, TouchableHighlight, FlatList
} from 'react-native';

import BookDetailComponent from "./BookDetailComponent";

export default class App extends Component<{}> {
    constructor(props) {
        super(props);
        this.id=0;

        this.getBooks = this.getBooks.bind(this);
        this.getBooksForFlatList = this.getBooksForFlatList.bind(this);
        this.getBookListElements = this.getBookListElements.bind(this);
        this.setDetailView = this.setDetailView.bind(this);
        this.getBookDetailComponent = this.getBookDetailComponent.bind(this);
        this.setBookListView = this.setBookListView.bind(this);
        this.handleUpdate = this.handleUpdate.bind(this);

        books = this.getBooks();
        viewElement = this.getBookListElements(books);
        this.state = {books: books, viewElement: viewElement}
    }

    getBooks() {
        return [
            {
                id: 0,
                title: "Gone with the wind",
                author: "Margareth Mitchael",
                publisher: "RAO",
                year: 1955,
                rating: 99,
                description: "Excellent book!"
            },
            {
                id: 1,
                title: "A new day",
                author: "Arthur Martin",
                publisher: "Adevarul",
                year: 1965,
                rating: 88,
                description: "Mysterious book with an interesting plot."
            },
            {
                id: 2,
                title: "The art of getting by",
                author: "Eva Miscente",
                publisher: "Penguin",
                year: 2007,
                rating: 59,
                description: "Good book for a rainy day."
            }
        ]
    }

    getBooksForFlatList(books) {
        books.map(x => x.key = x.id);
        return books;
    }

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
                                    {"Title: " + item.title + "\nAuthor: " + item.author + "\nRating: " + item.rating}
                                </Text>
                            </View>

                        </TouchableHighlight>
                    }
                />
            </View>
        );

    }

    setDetailView(bookId) {
        book = this.state.books.find(b => b.id === bookId);
        newElement = this.getBookDetailComponent(book);
        this.setState({viewElement: newElement});
    }


    getBookDetailComponent(book) {
        return <BookDetailComponent
            book={book}
            onUpdate={this.handleUpdate}
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
        this.setState({books: newBooks, viewElement: this.getBookListElements(this.state.books)});
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
        flex: 0.6,
        backgroundColor: 'white',
    },
    listItemView: {
        padding: 5
    },
    bigBlack: {
        fontSize: 15,
        fontWeight: 'bold',
    },
});
