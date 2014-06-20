#import "EduPlayerViewController.h"

@implementation EduPlayerViewController
@synthesize orientation=_orientation;

- (id)initWithContentURL:(NSURL *)url andOrientation:(BOOL)orientation
{
    EduPlayerViewController *o =  [[[self class] alloc] initWithContentURL:url];
    o.orientation = orientation;
    return o;
}

- (BOOL)shouldAutorotateToInterfaceOrientation:(UIInterfaceOrientation)interfaceOrientation
{
    // Return YES for supported orientations
    if (self.orientation) {
        return UIInterfaceOrientationIsPortrait(interfaceOrientation);
    } else {
       return UIInterfaceOrientationIsLandscape(interfaceOrientation); 
    }
}

@end
