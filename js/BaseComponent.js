import React, {Component} from 'react';
import {
    Text,
    View,
} from 'react-native';

export default class testRN extends Component {
    render() {
        return (
            <View style={{
                flex: 1,
                alignItems: 'center',
                // backgroundColor:'red',
            }}>
                <Text style={{color: "#44190e", fontSize: 30}}>this is ChildComponent back</Text>

            </View>
        );
    }
}