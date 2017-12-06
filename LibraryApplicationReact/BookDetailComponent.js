import React, {Component} from 'react';
import {
    Button,
    Platform, ScrollView,
    StyleSheet,
    Text,
    View,
    Alert, TouchableHighlight, FlatList, TextInput, Linking
} from 'react-native';
import DatePicker from 'react-native-datepicker'


export default class BookDetailComponent extends Component<{}> {
    constructor(props) {
        super(props);

        this.handleUpdate = this.handleUpdate.bind(this);
        this.sendEmail = this.sendEmail.bind(this);
        this.deleteBook = this.deleteBook.bind(this);
        this.alertDialogShow=this.alertDialogShow.bind(this);

        this.state = {
            title: this.props.book.title,
            author: this.props.book.author,
            publisher: this.props.book.publisher,
            date: this.props.book.date + "",
            rating: this.props.book.rating + "",
            description: this.props.book.description
        };
    }

    handleUpdate() {
        this.props.onUpdate({
            id: this.props.book.id,
            title: this.state.title,
            author: this.state.author,
            publisher: this.state.publisher,
            date: this.state.date,
            rating: this.state.rating,
            description: this.state.description
        })
    }

    sendEmail() {
        subject = "Book " + this.state.title + " details";
        body = "Title: " + this.state.title + "\n" +
            "Author: " + this.state.author + "\n" +
            "Publisher: " + this.state.publisher + "\n" +
            "Date of aparition: " + this.state.date + "\n" +
            "Rating: " + this.state.rating + "\n" +
            "Description: " + this.state.description;
        Linking.openURL('mailto:suciuirinacj@yahoo.com?subject=' + subject + '&body=' + body);
    }

    deleteBook() {
        this.props.onDelete(this.props.book.id);
    }

    alertDialogShow(){
        Alert.alert(
            'Confirm your choice',
            'Are you sure you want to remove this book?',
            [
                {text: 'YES', onPress: () => this.deleteBook()},
                {text: 'NO', onPress: () => console.log('Cancel Pressed'), style: 'cancel'},
            ],
            { cancelable: false }
        )
    }

    render() {
        return (
            <View style={styles.mainContainer}>

                <View>
                    <Text>Title: </Text>
                    <TextInput
                        onChangeText={(text) => this.setState({title: text})}
                        value={this.state.title}
                    />
                </View>
                <View>
                    <Text>Author: </Text>
                    <TextInput
                        onChangeText={(text) => this.setState({author: text})}
                        value={this.state.author}
                    />
                </View>
                <View>
                    <Text>Publisher: </Text>
                    <TextInput
                        onChangeText={(text) => this.setState({publisher: text})}
                        value={this.state.publisher}
                    />
                </View>
                <View>
                    <Text>Date of aparition: </Text>
                    <DatePicker
                        style={{width: 200}}
                        date={this.state.date}
                        mode="date"
                        placeholder="select date"
                        format="YYYY-MM-DD"
                        confirmBtnText="Confirm"
                        cancelBtnText="Cancel"
                        customStyles={{
                            dateIcon: {
                                position: 'absolute',
                                left: 0,
                                top: 4,
                                marginLeft: 0
                            },
                            dateInput: {
                                marginLeft: 36
                            }
                        }}
                        onDateChange={(date) => {
                            this.setState({date: date})
                        }}
                    />
                </View>

                <View>
                    <Text>Rating: </Text>
                    <TextInput
                        onChangeText={(text) => this.setState({rating: text})}
                        keyboardType={"numeric"}
                        maxLength={2}
                        value={this.state.rating}
                    />
                </View>

                <View>
                    <Text>Description: </Text>
                    <TextInput
                        value={this.state.description}
                        onChangeText={(text) => this.setState({description: text})}
                    />
                </View>


                <View style={styles.buttonContainer}>
                    <View style={{width: 130, padding: 5}}>
                        <Button
                            style={styles.buttonStyle}
                            title={"Back"}
                            color="#841584"
                            onPress={() => this.props.onComeBack()}
                        />
                    </View>
                    <View style={{width: 130, padding: 5}}>
                        <Button
                            style={styles.buttonStyle}
                            title={"Update"}
                            color="#841584"
                            onPress={() => this.handleUpdate()}
                        />
                    </View>
                    <View style={{width: 130, padding: 5}}>
                        <Button
                            style={styles.buttonStyle}
                            title={"Send Email"}
                            color="#841584"
                            onPress={() => this.sendEmail()}
                        />
                    </View>

                </View>
                <View style={styles.buttonContainer}>
                    <View style={{width: 130, padding: 5}}>
                        <Button
                            style={styles.buttonStyle}
                            title={"Delete"}
                            color="#841584"
                            onPress={() => this.alertDialogShow()}
                        />

                    </View>
                </View>
            </View>
        );
    }

}
const styles = StyleSheet.create({
    mainContainer: {
        flex: 1,
    },
    buttonContainer: {
        flex: 2,
        flexDirection: 'row',
        alignItems: 'center',
        justifyContent: 'center',
    },
    buttonStyle: {
        flex: 1,
    },
});
