const path = require('path');
const webpack = require('webpack');
const ExtractTextPlugin = require('extract-text-webpack-plugin');
const UglifyJSPlugin = require('uglifyjs-webpack-plugin');

module.exports = {
	devtool: 'source-map',
	entry: {
		detail: './src/detail.js',
		main: ['bootstrap/js/src/index', './src/main.js'],
	},
	output: {
		filename: '[name].js',
		path: path.resolve(__dirname, '../webapp/dist/')
	},
	module: {
		rules: [
			{
				test: /\.scss$/,
				use: ExtractTextPlugin.extract({
					use: [{loader: 'css-loader', options: {minimize: true}}, {loader: 'sass-loader'}],
					fallback: 'style-loader'
				})
			},
			{
				test: /\.css$/,
				use: ExtractTextPlugin.extract({
					fallback: 'style-loader',
					use: [{loader: 'css-loader', options: {minimize: true}}]
				})
			}
		]
	},
	plugins: [
		new ExtractTextPlugin({
			filename: '[name].css'
		}),
		new webpack.optimize.CommonsChunkPlugin({
			name: 'main',
			minChunks: 2
		}),
		new UglifyJSPlugin({
			sourceMap: true
		})
	]
};