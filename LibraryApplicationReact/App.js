import React, {Component} from 'react';
import {
    Platform,
    StyleSheet,
    Text,
    View
} from 'react-native';


export default class App extends Component<{}> {
    constructor(props) {
        super(props);

        this.getBooks = this.getBooks.bind(this);
        this.getBooksForFlatList = this.getBooksForFlatList.bind(this);
        this.getBookListElements=this.getBookListElements.bind(this);

        books = this.getBooks();
        viweElement=this.getBookListElements(books);
        this.state={books:books, viewElement:viewElement}
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
                    renderItem={({item})=>
                        <View style={styles.listItemView}>
                            <Text style={styles.bigBlack}>
                                {item.title+" "+ item.author+" "+item.rating}
                            </Text>
                        </View>
                    }
                />
            </View>
        );

    }

    render(){
        return this.state.books ===null ? null:(
            this.state.element
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
        padding: 10
    },
    bigBlack: {
        fontSize: 20,
        fontWeight: 'bold',
    },
});
