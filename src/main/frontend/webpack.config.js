const path = require('path');
const webpack = require('webpack');
const ExtractTextPlugin = require('extract-text-webpack-plugin');

module.exports = {
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
					use: [{loader: 'css-loader'}, {loader: 'sass-loader'}],
					fallback: 'style-loader'
				})
			},
			{
				test: /\.css$/,
				use: ExtractTextPlugin.extract({
					fallback: 'style-loader',
					use: ['css-loader']
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
		})
	]
};