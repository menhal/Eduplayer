//
//  EduPlayerViewController.h
//
//
//  Updated by Tom Krones 2013-09-30.
//  Created by Peter Robinett on 2012-10-15.
//
//  Based on phonegap-videoplayer-plugin by eiffel on 2011-11-09
//
//

#import <UIKit/UIKit.h>
#import "MediaPlayer/MPMoviePlayerViewController.h"

@interface EduPlayerViewController : MPMoviePlayerViewController {
    BOOL _orientation;
}

@property(nonatomic) BOOL orientation;

- (id)initWithContentURL:(NSURL *)url andOrientation:(BOOL)orientation;

@end
