require recipes-support/opencv/opencv_4.5.2.imx.bb

SRCBRANCH = "4.5.4_imx"
SRCREV_opencv = "4ec72bbcf1a999d5c59bc84c306f589aa491bb7f"
SRCREV_contrib = "2bc3e1a169afd2675ef68221176f32cc0ea5427a"
SRCREV_extra = "822ee11240b969e98e20546663bc481a5e7089cc"
SRC_URI:remove = " \
    file://0001-Use-the-one-argument-version-of-SetTotalBytesLimit.patch \
"

PACKAGECONFIG_OPENCL:mx8mnul = ""
PACKAGECONFIG_OPENCL:mx8mpul = ""
