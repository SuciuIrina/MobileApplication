import React, {Component} from 'react';
import {
    Button,
    Platform, ScrollView,
    StyleSheet,
    Text,
    View,
    Alert, TouchableHighlight, FlatList, TextInput,Linking
} from 'react-native';

export default class BookDetailComponent extends Component<{}> {
    constructor(props) {
        super(props);

        this.handleUpdate = this.handleUpdate.bind(this);
        this.sendEmail=this.sendEmail.bind(this);

        this.state = {
            title: this.props.book.title,
            author: this.props.book.author,
            publisher: this.props.book.publisher,
            year: this.props.book.year + "",
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
            year: this.state.year,
            rating: this.state.rating,
            description: this.state.description
        })
    }

    sendEmail() {
        subject = "Book " + this.state.title + " details";
        body = "Title: " + this.state.title + "\n" +
            "Author: " + this.state.author + "\n" +
            "Publisher: " + this.state.publisher + "\n" +
            "Year: " + this.state.year + "\n" +
            "Rating: " + this.state.rating + "\n" +
            "Description: " + this.state.description;
        Linking.openURL('mailto:suciuirinacj@yahoo.com?subject=' + subject + '&body=' + body);
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
                    <Text>Year: </Text>
                    <TextInput
                        onChangeText={(text) => this.setState({year: text})}
                        keyboardType={"numeric"}
                        maxLength={4}
                        value={this.state.year}
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
