DESCRIPTION = "The connect manager is provided by Quectel to enable LTE network"
SECTION = "quectel_cm"
LICENSE = "MIT" 
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

PN = "quectel_cm"
PR = "r0"

inherit systemd

QUECTEL_CM_URL = "git://github.com/nguyenvuhung/quectel-CM.git"
BRANCH = "master"
SRCREV = "5e3d6f2e8d56f5dc38cf597e61571ee4f394a12d"

SRC_URI = " \
	${QUECTEL_CM_URL};protocol=git;branch=${BRANCH} \
	"

SRC_URI_append = " \
	file://lte.service \
	"

S = "${WORKDIR}/git"
PV = "1.0"

EXTRA_OEMAKE += "'CC=${CC}' 'AR=${AR}' 'BUILDDIR=${S}'"
INSANE_SKIP_${PN} = "ldflags"

do_compile () {
    # Compile application
    cd ${S}/
    unset LDFLAGS
    oe_runmake all
}

do_install () {
    # create the /usr/bin folder in the rootfs give it default permissions
#    install -d ${D}${bindir}
    # move quectel application to /usr/bin folder. in the rootfs.
 #   install -m 0755 ${S}/quectel-cm ${D}${bindir}

    install -d ${D}${systemd_unitdir}/system
    install -m 644 ${WORKDIR}/lte.service ${D}${systemd_unitdir}/system
}

FILES_${PN} += "${libdir}/lib*.so"
FILES_${PN}-dev = "${libdir}/*.so"
FILES_${PN} += "quectel-cm"

SYSTEMD_SERVICE_${PN} = "lte.service"
