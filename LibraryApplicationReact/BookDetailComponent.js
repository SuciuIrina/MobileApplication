import React, {Component} from 'react';
import {
    Button,
    Platform, ScrollView,
    StyleSheet,
    Text,
    View,
    Alert, TouchableHighlight, FlatList, TextInput
} from 'react-native';

export default class BookDetailComponent extends Component<{}> {
    constructor(props) {
        super(props);

        this.handleUpdate=this.handleUpdate.bind(this);

        this.state = {
            title: this.props.book.title,
            author: this.props.book.author,
            publisher: this.props.book.publisher,
            year: this.props.book.year+"",
            rating: this.props.book.rating+"",
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
                    <Button
                        style={styles.buttonStyle}
                        title={"Update"}
                        onPress={() => this.handleUpdate()}
                    />
                    <Button
                        style={styles.buttonStyle}
                        title={"Back"}
                        onPress={() => this.props.onComeBack()}
                    />
                </View>
            </View>
        );
    }

}
const styles = StyleSheet.create({
    mainContainer: {
        flex: 1
    },
    buttonContainer: {
        flex: 1,
        flexDirection: 'row',
        alignItems: 'center',
        justifyContent: 'center'
    },
    buttonStyle:{
        flex:1,
        padding:10,
        width:30,
        color:"#841584",
    },
});
