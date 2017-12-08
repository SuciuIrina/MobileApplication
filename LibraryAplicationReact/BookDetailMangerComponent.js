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
import BookDetailComponent from "./BookDetailComponent";
import ChartComponent from "./ChartComponent";


export default class BookDetailManagerComponent extends Component<{}> {

    constructor(props) {
        super(props);
        this.loadChartComponent=this.loadChartComponent.bind(this);
        this.loadDetailComponent=this.loadDetailComponent.bind(this);

        let element=(<BookDetailComponent
            wishlist={this.props.wishlist}
            book={this.props.book}
            onUpdate={this.props.onUpdate}
            onDelete={this.props.onDelete}
            onComeBack={this.props.onComeBack}
            loadChart={this.loadChartComponent}
        />);
        this.state={element:element}

    }


    loadChartComponent(){
        let element=<ChartComponent
            onComeBack={this.loadDetailComponent}
            wishlist={this.props.wishlist}
        />
        this.setState({element:element})
    }
    loadDetailComponent(){
        let element=(<BookDetailComponent
            wishlist={this.props.whishlist}
            book={this.props.book}
            onUpdate={this.props.onUpdate}
            onDelete={this.props.onDelete}
            onComeBack={this.props.onComeBack}
            loadChart={this.loadChartComponent}
        />);
        this.setState({element:element})
    }
    render(){
        return this.state.element;
    }
}