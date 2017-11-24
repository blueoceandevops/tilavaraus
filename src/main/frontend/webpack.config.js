const path = require('path');
const webpack = require('webpack');
const ExtractTextPlugin = require('extract-text-webpack-plugin');
const UglifyJSPlugin = require('uglifyjs-webpack-plugin');
const WorkboxPlugin = require('workbox-webpack-plugin');
const CleanPlugin = require('clean-webpack-plugin');
const CopyWebpackPlugin = require('copy-webpack-plugin');

const dist = '../../../target/classes/static/';

module.exports = {
	devtool: 'source-map',
	entry: {
		'dist/detail': './src/detail.js',
		'service-worker': './src/service-worker.js',
		'dist/main': './src/main.js',
		'outdatedbrowser': ['outdatedbrowser/outdatedbrowser/outdatedbrowser.scss']
	},
	output: {
		filename: '[name].js',
		path: path.resolve(__dirname, dist)
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
		new CleanPlugin([dist], {allowExternal: true}),
		new ExtractTextPlugin({
			filename: '[name].css'
		}),
		new webpack.optimize.CommonsChunkPlugin({
			name: 'dist/main',
			chunks: ['dist/main', 'dist/detail']
		}),
		new webpack.DefinePlugin({
			PRECACHE: JSON.stringify(new Date().toISOString())
		}),
		new UglifyJSPlugin({
			sourceMap: true
		}),
		new CopyWebpackPlugin([
			{from: './node_modules/workbox-sw/build/importScripts/workbox-sw.prod.v2.1.2.js', to: dist},
			{from: './node_modules/outdatedbrowser/outdatedbrowser/outdatedbrowser.js', to: dist},
			{from: './node_modules/outdatedbrowser/outdatedbrowser/lang/', to: dist + 'lang/'}
		]),
		new WorkboxPlugin({
			globDirectory: dist,
			globPatterns: ['dist/**/*.{css,js}'],
			swDest: path.join(dist, 'service-worker.js'),
			swSrc: './src/service-worker.js'
		})
	]
};